package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 支付品牌
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/11
 */
public enum PayBrand implements IBaseEnum<String> {
    /**
     * 微信
     */
    WECHAT("WECHAT", "微信"),

    /**
     * 支付宝
     */
    ALIPAY("ALIPAY", "支付宝"),

    /**
     * 银行卡
     */
    BANK_CARD("BANK_CARD", "银行卡"),

    /**
     * 银联
     */
    UNIONPAY("UNIOPAY", "银联"),

    ;

    PayBrand(String code, String desc){
        initEnum(code, desc);
    }
}
