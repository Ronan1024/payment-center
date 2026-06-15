//package com.baosight.payment.channel.handler.notify.wechat;
//
//import cn.hutool.core.date.DateUtil;
//import com.baosight.payment.channel.enums.CallbackHandleStatus;
//import com.baosight.payment.channel.enums.ChannelEventType;
//import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
//import com.baosight.payment.channel.handler.notify.IChannelNotifyRule;
//import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
//import com.baosight.payment.channel.pojo.dao.WechatNotifyDTO;
//import com.baosight.payment.enums.ChannelCode;
//import com.baosight.utils.json.JsonUtil;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.util.StringUtils;
//
//import java.util.Date;
//
///**
// * 微信支付回调识别与解析规则。
// *
// * @author L.J.Ran
// * @date 2026/04/21
// */
//public abstract class WechatNotifyRule implements IChannelNotifyRule {
//
//    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//
//    /**
//     * 渠道编号
//     */
//    @Override
//    public ChannelCode channelCode() {
//        return ChannelCode.WECHAT_PAY;
//    }
//
//    /**
//     * 解析微信支付回调为统一结果。
//     *
//     * @param request 渠道回调入站请求
//     * @return 统一渠道回调结果
//     */
//    @Override
//    public UnifiedPayNotifyDTO parse(ChannelNotifyRequest request) {
//        WechatNotifyDTO notify = JsonUtil.parse(request.getBody(), WechatNotifyDTO.class);
//        return new UnifiedPayNotifyDTO()
//                .setChannelCode(ChannelCode.WECHAT_PAY)
//                .setEventType(resolveEventType(notify))
//                .setHandleStatus(resolveStatus(notify))
////                .setBizOrderNo(resolveText(request.getBody(), "out_trade_no", "out_refund_no", "id"))
//                .setChannelOrderNo(resolveText(request.getBody(), "transaction_id", "refund_id", "id"))
//                .setOriginChannelOrderNo(resolveText(request.getBody(), "original_transaction_id"))
//                .setFinishTime(resolveFinishTime(request.getBody(), notify))
//                .setRawBody(request.getBody());
//    }
//
//    /**
//     * 获取微信支付成功响应内容。
//     *
//     * @return 微信支付成功响应内容
//     */
//    @Override
//    public String successResponse() {
//        return "SUCCESS";
//    }
//
//    /**
//     * 执行处理
//     *
//     * @param dto 统一渠道回调结果
//     * @return true 表示处理成功
//     */
//    @Override
//    public Boolean process(UnifiedPayNotifyDTO dto) {
//        return null;
//    }
//
//    protected boolean isWechatNotify(ChannelNotifyRequest request) {
//        return request.getHeaders().containsKey("wechatpay-serial");
//    }
//
//    private ChannelEventType resolveEventType(WechatNotifyDTO notify) {
//        String originalType = notify.getResource() == null ? null : notify.getResource().getOriginalType();
//        if ("refund".equalsIgnoreCase(originalType) || containsIgnoreCase(notify.getEventType(), "REFUND")) {
//            return ChannelEventType.REFUND;
//        }
//        return ChannelEventType.PAY_ORDER;
//    }
//
//    private CallbackHandleStatus resolveStatus(WechatNotifyDTO notify) {
//        String eventType = notify.getEventType();
//        if (containsIgnoreCase(eventType, "SUCCESS")) {
//            return CallbackHandleStatus.SUCCESS;
//        }
//        if (containsIgnoreCase(eventType, "FAIL")
//                || containsIgnoreCase(eventType, "CLOSED")
//                || containsIgnoreCase(eventType, "ABNORMAL")) {
//            return CallbackHandleStatus.FAIL;
//        }
//        return CallbackHandleStatus.PROCESSING;
//    }
//
//    private Date resolveFinishTime(String body, WechatNotifyDTO notify) {
//        String value = resolveText(body, "success_time", "create_time");
//        if (!StringUtils.hasText(value)) {
//            value = notify.getCreateTime();
//        }
//        if (!StringUtils.hasText(value)) {
//            return null;
//        }
//        return DateUtil.parse(value);
//    }
//
//    private String resolveText(String body, String... names) {
//        try {
//            JsonNode root = OBJECT_MAPPER.readTree(body);
//            for (String name : names) {
//                JsonNode node = root.findValue(name);
//                if (node != null && !node.isNull()) {
//                    return node.asText();
//                }
//            }
//        } catch (Exception ignored) {
//            return null;
//        }
//        return null;
//    }
//
//    private boolean containsIgnoreCase(String value, String keyword) {
//        return value != null && value.toUpperCase().contains(keyword.toUpperCase());
//    }
//}
