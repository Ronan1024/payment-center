package com.baosight.payment.error;

import com.baosight.utils.enums.IBaseEnum;


public enum PayOrderError implements IBaseEnum<String> {
    /**
     * 订单状态异常
     */
    ORDER_STATUS_ERROR("10001", "订单状态异常"),
    /**
     * 商户订单已存在
     */
    ORDER_EXIST_ERROR("10002", "商户订单 [{}] 已存在"),
    /**
     * 异步通知地址协议仅支持http:// 或 https://
     */
    NOTIFY_URL_PROTOCOL_ERROR("10003", "异步通知地址协议仅支持http:// 或 https://"),
    /**
     * 同步通知地址协议仅支持http:// 或 https://
     */
    RETURN_URL_PROTOCOL_ERROR("10004", "同步通知地址协议仅支持http:// 或 https://"),
    /**
     * 商户应用状态不可用
     */
    APP_STATE_ERROR("10005", "商户应用 [{}] 状态不可用"),
    /**
     * 当前支付通道不可用
     */
    PAYMENT_CHANNEL_UNAVAILABLE("10006", "当前支付通道不可用"),
    /**
     * 接口不支持该支付方式
     */
    PAY_WAY_NOT_SUPPORT("10007", "接口不支持当前支付方式"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR("-1", "系统异常")
    ;

    PayOrderError(String code, String msg) {
        initEnum(code, msg);
    }
}
