package com.baosight.payment.order.task;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.math.Money;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.access.tl.model.TongLianClient;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.enums.DivisionState;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.order.mapper.OrderDivisionBatchMapper;
import com.baosight.payment.order.mapper.OrderDivisionRecordMapper;
import com.baosight.payment.order.mapper.PayOrderMapper;
import com.baosight.payment.order.pojo.entity.OrderDivisionBatch;
import com.baosight.payment.order.pojo.entity.OrderDivisionRecord;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.web.properties.ProjectInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 开始处理分账
 * // TODO 临时方案后续进行替换
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DivisionTask {
    @Value("${pay.notifyUrl}")
    private String notifyUrl;
    private final ProjectInfo projectInfo;
    private final PayOrderMapper payOrderMapper;
    private final MchAppConfigApi mchAppConfigApi;
    private final OrderDivisionBatchMapper orderDivisionBatchMapper;
    private final OrderDivisionRecordMapper orderDivisionRecordMapper;

    public void orderDivisionHandler() {
        List<PayOrder> orderList = payOrderMapper.selectList(new LambdaQueryWrapper<PayOrder>()
                .eq(PayOrder::getDivisionState, DivisionState.WAITING.getCode()));
        Map<Long, List<PayOrder>> payOrderMap = orderList.stream().collect(Collectors.groupingBy(PayOrder::getIsvId));
        payOrderMap.forEach((k, v) -> {
            String format = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
            OrderDivisionBatch orderDivisionBatch = orderDivisionBatchMapper.selectOne(new LambdaQueryWrapper<OrderDivisionBatch>()
                    .eq(OrderDivisionBatch::getDate, format)
                    .eq(OrderDivisionBatch::getClientId, k)
            );
            if (ObjectUtils.isEmpty(orderDivisionBatch)) {
                orderDivisionBatch = new OrderDivisionBatch();
                orderDivisionBatch.setDivisionState(DivisionState.WAITING.getCode());
                orderDivisionBatch.setClientId(k);
                orderDivisionBatch.setDate(format);
                orderDivisionBatchMapper.insert(orderDivisionBatch);
                Map<String, Object> map = new HashMap<>();
                List<String> list = v.stream().map(e -> {
                    SubAccountInfo subAccountInfo = new SubAccountInfo();
                    //商户订单号
                    subAccountInfo.setReqTraceNum(String.valueOf(e.getId()));
                    Money money = new Money(e.getWaitSettledAmount());
                    money = money.divide(100);
                    BigDecimal divide = new BigDecimal(e.getMchFeeRate()).divide(new BigDecimal(10000), 4, RoundingMode.HALF_UP);
                    Money multiply = money.multiply(divide);
                    long longValue = multiply.multiply(100).getAmount().longValue();
                    subAccountInfo.setCouponAmount(longValue);
                    subAccountInfo.setOrgRespTraceNum(e.getChannelOrderNo());
                    return subAccountInfo.toString();
                }).toList();
                TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfigDAO = mchAppConfigApi.tongLianIsvAndMchConfig(null, PayInterfaceCode.TONG_LIAN_PAY.getCode(), k);
                map.put("batchNo", String.valueOf(orderDivisionBatch.getId()));
                String string = writeListToByteString(list);
                log.info("分账文件数据: {}", string);
                map.put("respUrl", notifyUrl + "/api/division/notify");
                map.put("file", string);
                TongLianClient tongLianClient = new TongLianClient(tongLianIsvAndMchConfigDAO.isvConfig());
                TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2092", map);
                String url;
                if (projectInfo.hasDev()) {
                    url = "http://116.228.64.55:28082/yst-service-api/tx/handle";
                } else {
                    url = "https://ibsapi.allinpay.com/yst-service-api/tx/handle";
                }
                TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, url);
                if (Boolean.TRUE.equals(response.getSuccess())) {
                    orderDivisionBatch.setDivisionState(DivisionState.DIVISION_ING.getCode());
                    OrderDivisionBatch finalOrderDivisionBatch = orderDivisionBatch;
                    v.forEach(e -> {
                        OrderDivisionRecord divisionRecord = new OrderDivisionRecord();
                        divisionRecord.setOrderId(e.getId());
                        divisionRecord.setBatchId(finalOrderDivisionBatch.getId());
                        divisionRecord.setState(DivisionState.DIVISION_ING.getCode());
                        orderDivisionRecordMapper.insert(divisionRecord);
                    });
                } else {
                    orderDivisionBatch.setDivisionState(DivisionState.DIVISION_FAILURE.getCode());
                    orderDivisionBatch.setFailMsg(response.getErrorMsg());
                }
                orderDivisionBatchMapper.updateById(orderDivisionBatch);
            }
        });
    }


    @Data
    static class SubAccountInfo {
        /**
         * 商户订单号
         * <p>
         * 全局唯一不可重复
         */
        private String reqTraceNum;
        /**
         * 原收款的通联订单号
         */
        public String orgRespTraceNum;
        /**
         * 平台抽佣金额
         */
        private Long couponAmount;
        /**
         * “signNum、amount、remark”为一组数据，可以有多组；
         * <p>
         * 一组为一个分账人信息
         */
        private List<String> info;

        /**
         * 扩展信息
         */
        private String extendParams;

        @Override
        public String toString() {
            String result = reqTraceNum + "|" + orgRespTraceNum;
            if (!ObjectUtils.isEmpty(couponAmount) && couponAmount > 0) {
                result = result + "|" + couponAmount;
            } else {
                result = result + "|";
            }

            return result + "|" + "|";
        }
    }

    public String writeListToByteString(List<String> lines) {
        // 使用 ByteArrayOutputStream 来写入数据
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            for (String line : lines) {
                // 写入字符串
                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
                // 写入换行符
                outputStream.write("\n".getBytes(StandardCharsets.UTF_8));
            }
            // 获取字节数组
            byte[] byteArray = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(byteArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
