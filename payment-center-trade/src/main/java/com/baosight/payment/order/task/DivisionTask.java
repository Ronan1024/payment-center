package com.baosight.payment.order.task;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.math.Money;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import com.baosight.payment.settlement.api.MchAccountApi;
import com.baosight.payment.settlement.dto.MchAccountDTO;
import com.baosight.payment.settlement.enums.AccountType;
import com.baosight.web.properties.ProjectInfo;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.*;
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
    private final MchAccountApi mchAccountApi;
    private final MchAppConfigApi mchAppConfigApi;
    private final OrderDivisionBatchMapper orderDivisionBatchMapper;
    private final OrderDivisionRecordMapper orderDivisionRecordMapper;

    /**
     * 处理分账
     */
    @XxlJob("order-division-handler")
    public void orderDivisionHandler() {
        log.info("开始执行分账任务");
        List<PayOrder> orderList = payOrderMapper.selectList(new LambdaQueryWrapper<PayOrder>()
                .eq(PayOrder::getDivisionState, DivisionState.WAITING.code())
                .eq(PayOrder::getHasDivision, Boolean.TRUE)
                .le(PayOrder::getDivisionValidTime, new Date())
        );
        if (ObjectUtils.isEmpty(orderList)) {
            return;
        }
        Map<Long, List<PayOrder>> payOrderMap = orderList.stream().collect(Collectors.groupingBy(PayOrder::getIsvId));
        payOrderMap.forEach((k, v) -> {
            String format = DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN);
            List<Long> orderIdList = v.stream().map(PayOrder::getId).toList();
            OrderDivisionBatch orderDivisionBatch = new OrderDivisionBatch();
            orderDivisionBatch.setDivisionState(DivisionState.WAITING.code());
            orderDivisionBatch.setClientId(k);
            orderDivisionBatch.setDate(format);
            orderDivisionBatchMapper.insert(orderDivisionBatch);
            Map<String, Object> map = new HashMap<>();
            List<String> list = v.stream().map(e -> {
                // 渠道成本默认 2.6/1000
                BigDecimal divide = new BigDecimal("2.6")
                        .divide(new BigDecimal(1000), 4, RoundingMode.HALF_UP);
                SubAccountInfo subAccountInfo = new SubAccountInfo();
                //商户订单号
                subAccountInfo.setReqTraceNum(String.valueOf(e.getId()));
                Money money = new Money(e.getPayAmount() - e.getRefundAmount());
                money = money.divide(100);
                Money multiply = money.multiply(divide);
                long longValue = multiply.multiply(100).getAmount().longValue();
                // TODO 当前默认不进行抽成
                subAccountInfo.setCouponAmount(0L);
                subAccountInfo.setOrgRespTraceNum(e.getChannelOrderNo());
                return subAccountInfo.toString();
            }).toList();
            TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfigDAO = mchAppConfigApi.tongLianIsvAndMchConfig(null, PayInterfaceCode.TONG_LIAN_PAY.code(), k);
            map.put("batchNo", String.valueOf(orderDivisionBatch.getId()));
            String string = writeListToByteString(list);
            log.info("分账文件数据: {}", string);
            map.put("respUrl", notifyUrl + "/api/division/notify");
            map.put("totalCount", list.size());
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
            log.info("分账返回结果 : {}", response.getResult());
            log.info("分账请求返回结果信息:{}", response);
            if (Boolean.TRUE.equals(response.getSuccess())) {
                orderDivisionBatch.setDivisionState(DivisionState.DIVISION_ING.code());
                v.forEach(e -> {
                    OrderDivisionRecord divisionRecord = new OrderDivisionRecord();
                    divisionRecord.setOrderId(e.getId());
                    divisionRecord.setBatchId(orderDivisionBatch.getId());
                    divisionRecord.setState(DivisionState.DIVISION_ING.code());
                    divisionRecord.setDate(format);
                    divisionRecord.setChannelOrderId(e.getChannelOrderNo());
                    orderDivisionRecordMapper.insert(divisionRecord);
                    orderDivisionBatch.setChannelBatchId(response.get("batchNo").asText());
                });
                payOrderMapper.update(new LambdaUpdateWrapper<PayOrder>()
                        .in(PayOrder::getId, orderIdList)
                        .set(PayOrder::getDivisionState, DivisionState.DIVISION_ING.code()));
                log.info("分账成功");
            } else {
                orderDivisionBatch.setDivisionState(DivisionState.DIVISION_FAILURE.code());
                orderDivisionBatch.setFailMsg(response.getErrorMsg());
                payOrderMapper.update(new LambdaUpdateWrapper<PayOrder>()
                        .in(PayOrder::getId, orderIdList)
                        .set(PayOrder::getDivisionState, DivisionState.DIVISION_FAILURE.code()));
                log.info("分账失败");
            }
            orderDivisionBatchMapper.updateById(orderDivisionBatch);

        });
    }


    /**
     * 处理渠道分账成功的数据
     */
    @XxlJob("handler-division-success-date")
    public void handlerDivisionSuccessData() {
        log.info("开始处理渠道分账成功任务");

        String param = XxlJobHelper.getJobParam();
        List<Long> batchIdList = null;
        if (StringUtils.hasText(param)) {
            batchIdList = Arrays.stream(param.split(",")).map(Long::valueOf).toList();
        }
        List<OrderDivisionBatch> orderDivisionBatcheList = orderDivisionBatchMapper.selectList(new LambdaQueryWrapper<OrderDivisionBatch>()
                .eq(OrderDivisionBatch::getDivisionState, DivisionState.CHANNEL_HANDLER_SUCCESS.code())
                .in(!ObjectUtils.isEmpty(batchIdList), OrderDivisionBatch::getId, batchIdList));

        if (CollectionUtils.isEmpty(orderDivisionBatcheList)) {
            return;
        }

        // 开始处理
        orderDivisionBatcheList.forEach(e -> {
            TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfigDAO = mchAppConfigApi.tongLianIsvAndMchConfig(null, PayInterfaceCode.TONG_LIAN_PAY.code(), e.getClientId());
            TongLianClient tongLianClient = new TongLianClient(tongLianIsvAndMchConfigDAO.isvConfig());
            Map<String, Object> map = new HashMap<>();
            map.put("batchNo", String.valueOf(e.getId()));
            TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "4005", map);
            String url;
            if (projectInfo.hasDev()) {
                url = "http://116.228.64.55:28082/yst-service-api/tq/handle";
            } else {
                url = "https://ibsapi.allinpay.com/yst-service-api/tq/handle";
            }
            TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, url);
            log.info("批量分账回盘文件下载 : {}", response.getResult());
            if (response.success()) {
                log.info("开始处理回盘文件");
                String file = response.get("file").asText();
                log.info("解析到的文件数据: {}", file);

                log.info("开始处理分账任务：{}, 批次号{}", e.getDate(), e.getId());
                try {
                    List<DivisionFileParse> divisionFileParseList = fileParses(file);
                    log.info("解析到的数据：{}", divisionFileParseList);
                    if (!CollectionUtils.isEmpty(divisionFileParseList)) {
                        List<Long> successOrderList = new ArrayList<>();
                        Map<Long, String> failOrderList = new HashMap<>();
                        Map<Long, DivisionFileParse> parseMap = divisionFileParseList.stream().collect(Collectors.toMap(DivisionFileParse::getOrderId, divisionFileParse -> divisionFileParse));
                        List<OrderDivisionRecord> orderDivisionRecordList = orderDivisionRecordMapper.selectList(new LambdaQueryWrapper<OrderDivisionRecord>()
                                .eq(OrderDivisionRecord::getBatchId, e.getId()));

                        orderDivisionRecordList.forEach(orderDivisionRecord -> {
                            DivisionFileParse divisionFileParse = parseMap.get(orderDivisionRecord.getOrderId());
                            if (divisionFileParse.getTradeStatus().equals(1)) {
                                successOrderList.add(orderDivisionRecord.getOrderId());
                            } else if (divisionFileParse.getTradeStatus().equals(2)) {
                                failOrderList.put(divisionFileParse.orderId, divisionFileParse.failReason);
                            }
                        });
                        // 修改成功
                        if (!CollectionUtils.isEmpty(successOrderList)) {
                            orderDivisionRecordMapper.update(new LambdaUpdateWrapper<OrderDivisionRecord>()
                                    .in(OrderDivisionRecord::getOrderId, successOrderList)
                                    .set(OrderDivisionRecord::getState, DivisionState.DIVISION_SUCCESS.code())
                            );
                            List<PayOrder> payOrders = payOrderMapper.selectByIds(successOrderList);
                            Map<Long, List<PayOrder>> collect = payOrders.stream().collect(Collectors.groupingBy(PayOrder::getMchId));
                            List<MchAccountDTO> mchAccountDTOList = new ArrayList<>();
                            collect.forEach((k, v) -> {
                                payOrderMapper.update(new LambdaUpdateWrapper<PayOrder>()
                                        .in(PayOrder::getId, v.stream().map(PayOrder::getId).toList())
                                        .set(PayOrder::getDivisionState, DivisionState.DIVISION_SUCCESS.code())
                                );
                                MchAccountDTO mchAccountDTO = new MchAccountDTO();
                                mchAccountDTO.setType(AccountType.SUCCESS.code());
                                mchAccountDTO.setMchId(k);
                                List<Money> list = v.stream().map(order -> {
                                    DivisionFileParse divisionFileParse = parseMap.get(order.getId());
                                    return divisionFileParse.getAmount();
                                }).toList();
                                mchAccountDTO.setAmount(list);
                                mchAccountDTOList.add(mchAccountDTO);
                            });
                            Boolean account = mchAccountApi.changeMchAccount(mchAccountDTOList);
                            if (account && CollectionUtils.isEmpty(failOrderList)) {
                                orderDivisionBatchMapper.update(new LambdaUpdateWrapper<OrderDivisionBatch>()
                                        .eq(OrderDivisionBatch::getId, e.getId())
                                        .set(OrderDivisionBatch::getDivisionState, DivisionState.DIVISION_SUCCESS.code())
                                );
                            }
                        }
                        if (!CollectionUtils.isEmpty(failOrderList)) {
                            failOrderList.forEach((k, v) -> {
                                orderDivisionRecordMapper.update(new LambdaUpdateWrapper<OrderDivisionRecord>()
                                        .eq(OrderDivisionRecord::getOrderId, k)
                                        .set(OrderDivisionRecord::getState, DivisionState.DIVISION_FAILURE.code())
                                        .set(OrderDivisionRecord::getErrMsg, v)
                                );
                            });
                            payOrderMapper.update(new LambdaUpdateWrapper<PayOrder>()
                                    .in(PayOrder::getId, failOrderList.keySet())
                                    .set(PayOrder::getDivisionState, DivisionState.DIVISION_FAILURE.code())
                            );
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    log.info("分账任务处理失败：{}, 批次号{}", e.getDate(), e.getId());
                    throw exception;

                }
            } else {
                log.error("批量分账回盘文件下载 请求失败 批次号：{} 异常信息: {}", e.getId(), response.getResult());
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
            //trx001|20240711123456|||
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


    public static void main1(String[] args) {
        //{"file":"MTkxMjE3NjY2MzU5MjIyNjgxN3wyMDI1MDQyMDE5MDYxNzIwOTIwMDUxNDI2OHwyMDI1MDQxNjAwMTAxNzIwODUwMTUxNjQ4N3xudWxsfDB8fHwyfG9yZ1Jlc3BUcmFjZU51be+8mjIwMjUwNDE3MDk0MDA1MjA4NTAxODQ1MzQ05peg57uT566X6YeR6aKd77yM5LiN5YWB6K645YiG6LSmCjE5MTIzMjgwODUxNTU2NjM4NzR8MjAyNTA0MjAxOTA2MTcyMDkyMDA1MTQyNjl8MjAyNTA0MTYxMDExNTkyMDg1MDE4NDc2Njd8bnVsbHwwfHx8MnxvcmdSZXNwVHJhY2VOdW3vvJoyMDI1MDQxNzA5MzcwNjIwODUwMTUxNTIzNeaXoOe7k+eul+mHkemine+8jOS4jeWFgeiuuOWIhui0pgoxOTEyMzI4Mjk4ODk3Mzk1NzE0fDIwMjUwNDIwMTkwNjE3MjA5MjAwNTE0MjcwfDIwMjUwNDE2MTAxMjUwMjA4NTAxODQ3NzY0fG51bGx8MHx8fDJ8b3JnUmVzcFRyYWNlTnVt77yaMjAyNTA0MTcwOTM3MDYyMDg1MDE4NDUyMzfml6Dnu5Pnrpfph5Hpop3vvIzkuI3lhYHorrjliIbotKYK","respCode":"00000","batchNo":"1913912096831361026","transDate":"20250420","respMsg":"交易成功"}
//        String base64Str = "MTkxMjE3NjY2MzU5MjIyNjgxN3wyMDI1MDQxNzE2NTcyOTIwOTIwMDU3MzAwNXwyMDI1MDQxNjAwMTAxNzIwODUwMTUxNjQ4N3xudWxsfDB8fHwyfG9yZ1Jlc3BUcmFjZU51be+8mjIwMjUwNDE3MDk0MDA1MjA4NTAxODQ1MzQ05peg57uT566X6YeR6aKd77yM5LiN5YWB6K645YiG6LSmCg==";
//        String base64Str = "MTkxMjE3NjY2MzU5MjIyNjgxN3wyMDI1MDQyMTEzNDM1NzIwOTIwMDUxOTE4M3wyMDI1MDQxNjAwMTAxNzIwODUwMTUxNjQ4N3xudWxsfDB8fHwyfG9yZ1Jlc3BUcmFjZU51be+8mjIwMjUwNDE3MDk0MDA1MjA4NTAxODQ1MzQ05peg57uT566X6YeR6aKd77yM5LiN5YWB6K645YiG6LSmCjE5MTIzMjgwODUxNTU2NjM4NzR8MjAyNTA0MjExMzQzNTcyMDkyMDA1MTkxODR8MjAyNTA0MTYxMDExNTkyMDg1MDE4NDc2Njd8bnVsbHwwfHx8MnxvcmdSZXNwVHJhY2VOdW3vvJoyMDI1MDQxNzA5MzcwNjIwODUwMTUxNTIzNeaXoOe7k+eul+mHkemine+8jOS4jeWFgeiuuOWIhui0pgoxOTEyMzI4Mjk4ODk3Mzk1NzE0fDIwMjUwNDIxMTM0MzU3MjA5MjAwNTE5MTg1fDIwMjUwNDE2MTAxMjUwMjA4NTAxODQ3NzY0fG51bGx8MHx8fDJ8b3JnUmVzcFRyYWNlTnVt77yaMjAyNTA0MTcwOTM3MDYyMDg1MDE4NDUyMzfml6Dnu5Pnrpfph5Hpop3vvIzkuI3lhYHorrjliIbotKYK";
        String base64Str = "MTkxMjE3NjY2MzU5MjIyNjgxN3wyMDI1MDQyMTE1MDM0MjIwOTIwMTQ0ODE5NHwyMDI1MDQxNzA5NDAwNTIwODUwMTg0NTM0NHwyMDl8MHx8MjAyNTA0MjExNTAzNDJ8MXzkuqTmmJPmiJDlip8KMTkxMjMyODA4NTE1NTY2Mzg3NHwyMDI1MDQyMTE1MDM0MjIwOTIwMTMwODE5M3wyMDI1MDQxNzA5MzcwNjIwODUwMTUxNTIzNXwyMDl8MHx8MjAyNTA0MjExNTAzNDJ8MXzkuqTmmJPmiJDlip8KMTkxMjMyODI5ODg5NzM5NTcxNHwyMDI1MDQyMTE1MDM0MjIwOTIwMTMwODE5MnwyMDI1MDQxNzA5MzcwNjIwODUwMTg0NTIzN3w1MHwwfHwyMDI1MDQyMTE1MDM0MnwxfOS6pOaYk+aIkOWKnwoxOTEyNDUyOTI0Mjg5ODk2NDUwfDIwMjUwNDIxMTUwMzQyMjA5MjAxNDQ4MTk1fDIwMjUwNDE3MDk1MDM1MjA4NTAxNTE1ODkzfDUwfDB8fDIwMjUwNDIxMTUwMzQyfDF85Lqk5piT5oiQ5YqfCg==";
//        List<DivisionFileParse> divisionFileParses = divisionTask.fileParses(base64Str);

        byte[] decodedBytes = Base64.getDecoder().decode(base64Str);
//
        try (InputStream inputStream = new ByteArrayInputStream(decodedBytes);
             InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
//                String[] split = line.split("\\|");
                System.out.println("读取到一行: " + line);
                // 你可以在这里进行后续逻辑处理
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(divisionFileParses);
    }

    private List<DivisionFileParse> fileParses(String base64Str) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Str);
        List<DivisionFileParse> result = new ArrayList<>();

        try (InputStream inputStream = new ByteArrayInputStream(decodedBytes);
             InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                DivisionFileParse divisionFileParse = new DivisionFileParse();
                String[] split = line.split("\\|");
                divisionFileParse.setOrderId(Long.valueOf(split[0]));
                Integer orderState = Integer.valueOf(split[7]);
                if (orderState.equals(1)) {
                    // 交易成功
                    String orderAmount = split[3];
                    Money divide = new Money(orderAmount).divide(new BigDecimal(100));
                    divisionFileParse.setAmount(divide);
                } else if (orderState.equals(2)) {
                    // 交易失败
                    String failReason = split[8];
                    divisionFileParse.setFailReason(failReason);
                } else {
                    // 处理中
                }
                divisionFileParse.setTradeStatus(orderState);
                result.add(divisionFileParse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        String file = "MTkyMjYyODgxODU4OTUwMzQ5MHwyMDI1MDUyNDA5NTAwMDIwOTIwMDMzODY0MXwyMDI1MDUyMzA5NDQ0OTIwODUwMTY0NjY1MXxudWxsfDB8fHwyfG9yZ1Jlc3BUcmFjZU51be+8mjIwMjUwNTIzMDk0NDQ5MjA4NTAxNjQ2NjUx5YiG6LSm5qCH6K+G6Z2e5b6F5YiG6LSmCg==";
        byte[] decodedBytes = Base64.getDecoder().decode(file);
        List<DivisionFileParse> result = new ArrayList<>();

        try (InputStream inputStream = new ByteArrayInputStream(decodedBytes);
             InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("读取到一行: " + line);
                DivisionFileParse divisionFileParse = new DivisionFileParse();
                String[] split = line.split("\\|");
                divisionFileParse.setOrderId(Long.valueOf(split[0]));


                if (StringUtils.hasText(split[3])) {
                    Money divide = new Money(split[3]).divide(new BigDecimal(100));
                    divisionFileParse.setAmount(divide);
                }
                divisionFileParse.setTradeStatus(Integer.valueOf(split[7]));
                divisionFileParse.setFailReason(split[8]);
                // 你可以在这里进行后续逻辑处理
                result.add(divisionFileParse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Data
    static class DivisionFileParse {
        /**
         * 商户订单号
         */
        private Long orderId;
        /**
         * 交易状态
         */
        private Integer tradeStatus;

        /**
         * 失败原因
         */
        private String failReason;
        /**
         * 渠道订单号
         */
        private String channelOrderId;
        /**
         * 渠道原订单号
         */
        private String channelOriginalOrderId;
        /**
         * 订单金额
         */
        private Money amount;
    }
}
