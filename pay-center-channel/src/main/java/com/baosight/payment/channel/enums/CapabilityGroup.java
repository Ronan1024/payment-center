package com.baosight.payment.channel.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */

public enum CapabilityGroup implements IBaseEnum<String> {
    /**
     * 支付
     */
    PAYMENT("PAYMENT", "支付"),
    ;

    CapabilityGroup(String key, String msg) {
        initEnum(key, msg);
    }
}
