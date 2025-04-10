package com.baosight.payment.system.error;

import com.baosight.utils.enums.IBaseEnum;

/**
 * 支付接口配置异常
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
public enum PayInterfaceConfigError implements IBaseEnum<String> {
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
    ;

    PayInterfaceConfigError(String code, String msg) {
        initEnum(code, msg);
    }
}
