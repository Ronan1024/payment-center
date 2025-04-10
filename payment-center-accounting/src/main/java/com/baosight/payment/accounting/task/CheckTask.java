package com.baosight.payment.accounting.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baosight.payment.accounting.enums.ChannelBillHandlerState;
import com.baosight.payment.accounting.enums.CheckBatchRecordState;
import com.baosight.payment.accounting.enums.CheckState;
import com.baosight.payment.accounting.manager.ChannelBillManager;
import com.baosight.payment.accounting.manager.CheckManager;
import com.baosight.payment.accounting.mapper.ChannelBillMapper;
import com.baosight.payment.accounting.mapper.CheckBatchRecordMapper;
import com.baosight.payment.accounting.mapper.CheckRecordMapper;
import com.baosight.payment.accounting.pojo.entity.ChannelBill;
import com.baosight.payment.accounting.pojo.entity.CheckBatchRecord;
import com.baosight.payment.accounting.pojo.entity.CheckRecord;
import com.baosight.payment.enums.TradeType;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.vo.OrderVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 对账任务
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/8
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CheckTask {
    private final CheckManager checkManager;
    private final ChannelBillManager channelBillManager;
    private final CheckRecordMapper checkRecordMapper;
    private final CheckBatchRecordMapper checkBatchRecordMapper;
    private final ChannelBillMapper channelBillMapper;
    private final OrderApi orderApi;

    /**
     * 创建交易对账并处理
     */
    @XxlJob("pay-create-check-record")
    public void createAndHandleCheck() {
        try {
            // TODO 临时解决方案待处理 P0
            List<ChannelBill> channelBillList = channelBillManager.getChannelGroup(ChannelBillHandlerState.PENDING.getCode());
            channelBillList.forEach(e -> {
                String checkBatchCode = e.getChannelCode() + "-" + e.getBillDate() + "-" + e.getChannelMchNo() + "-" + e.getTradeType();
                CheckBatchRecord batchRecord = checkBatchRecordMapper.selectOne(new LambdaQueryWrapper<CheckBatchRecord>()
                        .eq(CheckBatchRecord::getCheckBatchCode, checkBatchCode));
                if (ObjectUtils.isEmpty(batchRecord)) {
                    batchRecord = new CheckBatchRecord();
                    batchRecord.setCheckBatchCode(checkBatchCode);
                    batchRecord.setState(CheckBatchRecordState.PROCESSING.getCode());
                    batchRecord.setBillDate(e.getBillDate());
                    batchRecord.setInterfaceCode(e.getChannelCode());
                    batchRecord.setInterfaceId(e.getChannelId());
                    batchRecord.setChannelMchNo(e.getChannelMchNo());
                    checkBatchRecordMapper.insert(batchRecord);
                }

                //创建批次并创建对账信息
                if (e.getTradeType().equals(TradeType.CONSUMPTION.getCode())) {
                    // 消费订单
                    List<ChannelBill> billList = channelBillMapper.selectList(new LambdaQueryWrapper<ChannelBill>()
                            .eq(ChannelBill::getBillDate, e.getBillDate())
                            .eq(ChannelBill::getTradeType, e.getTradeType())
                            .eq(ChannelBill::getChannelMchNo, e.getChannelMchNo())
                            .eq(ChannelBill::getChannelCode, e.getChannelCode())
                            .eq(ChannelBill::getBillState, ChannelBillHandlerState.PENDING.getCode()));
                    List<Long> orderIdList = billList.stream().map(ChannelBill::getOrderId).toList();
                    List<OrderVO> orderList = orderApi.orderList(orderIdList);
                    Map<Long, OrderVO> collect = orderList.stream().collect(Collectors.toMap(OrderVO::getId, order -> order));

                    List<Long> successBillList = new ArrayList<>();
                    List<Long> failBillList = new ArrayList<>();
                    CheckBatchRecord finalBatchRecord = batchRecord;
                    billList.forEach(bill -> {
                        CheckRecord record = checkRecordMapper.selectOne(new LambdaQueryWrapper<CheckRecord>()
                                .eq(CheckRecord::getChannelOrderId, bill.getChannelOrderId())
                        );
                        if (ObjectUtils.isEmpty(record)) {
                            CheckRecord checkRecord = new CheckRecord();
                            checkRecord.setChannelOrderId(bill.getChannelOrderId());
                            checkRecord.setChannelAmount(bill.getTradingAmount());
                            checkRecord.setCheckBatchId(finalBatchRecord.getId());
                            checkRecord.setCheckBatchCode(finalBatchRecord.getCheckBatchCode());
                            checkRecord.setTradeType(bill.getTradeType());
                            if (collect.containsKey(bill.getOrderId())) {
                                OrderVO orderVO = collect.get(bill.getOrderId());
                                checkRecord.setAmount(orderVO.getPayAmount());
                                checkRecord.setOrderId(orderVO.getId());
                                if (orderVO.getPayAmount().equals(bill.getTradingAmount()) && orderVO.getState().equals(bill.getTradingState())) {
                                    successBillList.add(bill.getId());
                                } else {
                                    checkRecord.setCheckState(CheckState.MISTAKE.getCode());
                                    failBillList.add(bill.getId());
                                }
                            } else {
                                checkRecord.setCheckState(CheckState.CHANNEL_OVER.getCode());
                                failBillList.add(bill.getId());
                            }
                            checkRecordMapper.insert(checkRecord);
                        }
                    });
                    if (!successBillList.isEmpty()) {
                        // 处理正常的
                        channelBillMapper.update(new LambdaUpdateWrapper<ChannelBill>()
                                .in(ChannelBill::getId, successBillList)
                                .set(ChannelBill::getBillState, ChannelBillHandlerState.COMPLETED.getCode())
                        );
                        orderApi.updateOrderCheckState(successBillList);
                    }
                    channelBillMapper.update(new LambdaUpdateWrapper<ChannelBill>()
                            .in(ChannelBill::getId, failBillList)
                            .set(ChannelBill::getBillState, ChannelBillHandlerState.PROCESSING.getCode())
                    );
                    if (failBillList.isEmpty()) {
                        checkBatchRecordMapper.update(new LambdaUpdateWrapper<CheckBatchRecord>()
                                .eq(CheckBatchRecord::getId, batchRecord.getId())
                                .set(CheckBatchRecord::getState, CheckBatchRecordState.SUCCESS.getCode())
                        );
                    } else {
                        checkBatchRecordMapper.update(new LambdaUpdateWrapper<CheckBatchRecord>()
                                .eq(CheckBatchRecord::getId, batchRecord.getId())
                                .set(CheckBatchRecord::getState, CheckBatchRecordState.DIFFERENCES_PROCESSING.getCode())
                        );
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
