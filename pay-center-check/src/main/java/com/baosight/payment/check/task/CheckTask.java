package com.baosight.payment.check.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baosight.payment.check.enums.ChannelBillHandlerState;
import com.baosight.payment.check.enums.CheckBatchRecordState;
import com.baosight.payment.check.enums.CheckState;
import com.baosight.payment.check.enums.TradingFlowState;
import com.baosight.payment.check.manager.ChannelBillManager;
import com.baosight.payment.check.manager.TradingFlowManager;
import com.baosight.payment.check.mapper.ChannelBillMapper;
import com.baosight.payment.check.mapper.CheckBatchRecordMapper;
import com.baosight.payment.check.mapper.CheckRecordMapper;
import com.baosight.payment.check.pojo.entity.ChannelBill;
import com.baosight.payment.check.pojo.entity.CheckBatchRecord;
import com.baosight.payment.check.pojo.entity.CheckRecord;
import com.baosight.payment.check.pojo.entity.TradingFlow;
import com.baosight.payment.enums.OrderType;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;
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
    private final ChannelBillManager channelBillManager;
    private final CheckRecordMapper checkRecordMapper;
    private final CheckBatchRecordMapper checkBatchRecordMapper;
    private final ChannelBillMapper channelBillMapper;
    private final TradingFlowManager tradingFlowManager;

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
                if (e.getTradeType().equals(OrderType.CONSUMPTION.getCode())) {
                    // 消费订单
                    List<ChannelBill> billList = channelBillMapper.selectList(new LambdaQueryWrapper<ChannelBill>()
                            .eq(ChannelBill::getBillDate, e.getBillDate())
                            .eq(ChannelBill::getTradeType, e.getTradeType())
                            .eq(ChannelBill::getChannelMchNo, e.getChannelMchNo())
                            .eq(ChannelBill::getChannelCode, e.getChannelCode())
                            .eq(ChannelBill::getBillState, ChannelBillHandlerState.PENDING.getCode()));

                    List<TradingFlow> tradingFlowList = tradingFlowManager.list(new LambdaQueryWrapper<TradingFlow>()
                            .eq(TradingFlow::getTradingType, e.getTradeType())
                            .eq(TradingFlow::getDate, e.getBillDate())
                            .eq(TradingFlow::getChannelMchNo, e.getChannelMchNo())
                            .eq(TradingFlow::getChannelCode, e.getChannelCode())
                            .eq(TradingFlow::getState, TradingFlowState.PENDING.getCode()));


                    Map<Long, TradingFlow> tradingFlowMap = tradingFlowList.stream().collect(Collectors.toMap(TradingFlow::getOrderId, tradingFlow -> tradingFlow));

                    List<Long> successBillList = new ArrayList<>();
                    List<Long> successTradingFlowList = new ArrayList<>();
                    List<Long> failBillList = new ArrayList<>();
                    Set<Long> settlementSet = new HashSet<>();
                    CheckBatchRecord finalBatchRecord = batchRecord;
                    billList.forEach(bill -> {
                        CheckRecord record = checkRecordMapper.selectOne(new LambdaQueryWrapper<CheckRecord>()
                                .eq(CheckRecord::getChannelOrderId, bill.getChannelOrderId()));
                        if (ObjectUtils.isEmpty(record)) {
                            CheckRecord checkRecord = new CheckRecord();
                            checkRecord.setChannelOrderId(bill.getChannelOrderId());
                            checkRecord.setChannelAmount(bill.getTradingAmount());
                            checkRecord.setCheckBatchId(finalBatchRecord.getId());
                            checkRecord.setCheckBatchCode(finalBatchRecord.getCheckBatchCode());
                            checkRecord.setTradeType(bill.getTradeType());
                            if (tradingFlowMap.containsKey(bill.getOrderId())) {
                                TradingFlow tradingFlow = tradingFlowMap.get(bill.getOrderId());
                                checkRecord.setAmount(tradingFlow.getAmount());
                                checkRecord.setOrderId(tradingFlow.getId());
                                if (tradingFlow.getAmount().equals(bill.getTradingAmount()) && tradingFlow.getTradingState().equals(bill.getTradingState())) {
                                    successBillList.add(bill.getId());
                                    successTradingFlowList.add(tradingFlow.getId());
                                    if (tradingFlow.getTradingType().equals(OrderType.CONSUMPTION.getCode())) {
                                        settlementSet.add(tradingFlow.getOrderId());
                                    }else {
                                        settlementSet.add(tradingFlow.getOriginOrderId());
                                    }
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
                        // 对账成功
                        tradingFlowManager.update(new LambdaUpdateWrapper<TradingFlow>()
                                .in(TradingFlow::getId, successTradingFlowList)
                                .set(TradingFlow::getState, TradingFlowState.COMPLETED.getCode())
                        );
                        // TODO 通知结算中心
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
