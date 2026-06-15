package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
public enum CapabilityRealm implements IBaseEnum<String> {
    /**
     * 支付
     */
    PAYMENT("PAYMENT", "支付"),

    /**
     * 会员
     */
    MEMBER("MEMBER", "会员"),
    ;

    CapabilityRealm(String code, String name) {
        initEnum(code, name);
    }
}
