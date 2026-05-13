package com.baosight.payment.channel.error;


import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
public enum ChannelError implements IErrorEnum<String> {
    /**
     * 渠道编号异常无法进行装配
     */
    CHANNEL_CODE_ERROR("A000010", "渠道编号异常"),
    /**
     * 当前渠道已存在
     */
    CHANNEL_EXIST("A000011", "当前渠道已存在"),
    /**
     * 当前渠道编号已绑定
     */
    CHANNEL_CODE_BOUND_ALREADY("A000012", "渠道编号已绑定"),
    /**
     * 渠道信息不存在
     */
    CHANNEL_INFO_NOT_EXIST ("A000013", "渠道信息不存在"),
    /**
     * 未找到渠道回调业务处理器
     */
    CALLBACK_HANDLER_NOT_FOUND("A000014", "未找到渠道回调业务处理器"),
    /**
     * 未找到渠道回调识别规则
     */
    CALLBACK_RULE_NOT_FOUND("A000019", "未找到渠道回调识别规则"),
    /**
     * 渠道回调事件类型异常
     */
    CALLBACK_EVENT_TYPE_ERROR("A000020", "渠道回调事件类型异常"),
    /**
     * 渠道回调事件类型不匹配
     */
    CALLBACK_EVENT_TYPE_NOT_MATCH("A000021", "渠道回调事件类型不匹配"),
    /**
     * 渠道回调渠道不匹配
     */
    CALLBACK_CHANNEL_NOT_MATCH("A000022", "渠道回调渠道不匹配"),
    /**
     * 支付回调未识别到系统支付订单号
     */
    CALLBACK_PAY_ORDER_ID_MISSING("A000015", "支付回调未识别到系统支付订单号"),
    /**
     * 退款回调未识别到系统退款订单号
     */
    CALLBACK_REFUND_ORDER_ID_MISSING("A000016", "退款回调未识别到系统退款订单号"),
    /**
     * 支付订单不存在
     */
    CALLBACK_PAY_ORDER_NOT_FOUND("A000017", "支付订单不存在"),
    /**
     * 退款订单不存在
     */
    CALLBACK_REFUND_ORDER_NOT_FOUND("A000018", "退款订单不存在"),

    ;


    ChannelError(String code, String message) {
        initEnum(code, message);
    }
}
