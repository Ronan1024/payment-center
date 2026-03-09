package com.baosight.payment.system.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * 支付接口配置异常
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
public enum PayInterfaceConfigError implements IErrorEnum<String> {
    /**
     * 商户未配置当前支付接口
     */
    MERCHANT_NOT_CONFIG_PAY_INTERFACE("60001", "商户未配置当前支付接口"),
    /**
     * 当前服务商未配置支付接口
     */
    ISV_NOT_CONFIG_PAY_INTERFACE("60003", "当前服务商未配置支付接口"),
    /**
     * 支付费率未配置
     */
    PAY_RATE_NOT_CONFIG("60002", "支付费率未配置"),

    /**
     * 表单字段不能为空
     */
    FORM_FIELD_CANNOT_BE_EMPTY("60004", "提交配置中 {} 不能为空"),
    /**
     * 当前商户未获取到当前通道权限
     */
    MERCHANT_HAS_NO_CHANNEL_PERMISSION("60005", "当前商户未获取到当前通道权限"),
    ;

    PayInterfaceConfigError(String code, String msg) {
        initEnum(code, msg);
    }
}
