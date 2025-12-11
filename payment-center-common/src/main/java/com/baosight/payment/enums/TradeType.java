package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 交易类型
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/5
 */
public enum TradeType implements IBaseEnum<String> {
    /**
     * 微信支付
     */
    WECHAT_PAY("300", "微信支付"),
    /**
     * 微信退款
     */
    WECHAT_REFUND("301", "微信退款"),
    /**
     * 微信取消支付
     */
    WECHAT_CANCEL("302", "微信取消支付"),
    /**
     * 支付宝支付
     */
    ALIPAY_PAY("310", "支付宝支付"),
    /**
     * 支付宝退款
     */
    ALIPAY_REFUND("311", "支付宝退款"),
    /**
     * 支付宝取消支付
     */
    ALIPAY_CANCEL("312", "支付宝取消支付"),
    ;

    TradeType(String code, String msg) {
        initEnum(code, msg);
    }
}
