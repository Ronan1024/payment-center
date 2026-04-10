package com.baosight.payment.system.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/26
 */
public enum PaymentChannelFlowError implements IErrorEnum<String> {
    /**
     * 当前支付类型已被使用
     */
    PAYMENT_TYPE_ALREADY_USED("1000001", "当前支付类型已被使用"),
    /**
     * 支付渠道流程不存在
     */
    PAYMENT_CHANNEL_FLOW_NOT_EXIST("1000002", "支付渠道流程不存在"),

    /**
     * 商户渠道流程数据异常
     */
    MERCHANT_CHANNEL_FLOW_DATA_EXCEPTION("1000003", "商户渠道流程数据异常"),
    /**
     *  当前流程正在处理中请稍后刷新后重试
     */
    MERCHANT_CHANNEL_FLOW_PROCESSING("1000004", "当前流程正在处理中请稍后刷新后重试"),

    /**
     * 支付渠道流程步骤顺序无效
     */
    PAYMENT_CHANNEL_FLOW_STEP_ORDER_INVALID("1000005", "支付渠道流程步骤顺序无效"),

    /**
     * 当前渠道编号已存在
     */
    PAYMENT_CHANNEL_FLOW_CODE_ALREADY_EXIST("1000006", "当前渠道编号已存在"),


    ;
    
    PaymentChannelFlowError(String code,String msg){
        initEnum(code, msg);
    }
}
