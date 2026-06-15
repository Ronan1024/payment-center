package com.baosight.payment.channel.enums;

import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */

@Getter
public enum CapabilityEnum implements IBaseEnum<String> {
    /**
     * 微信 H5 支付
     */
    WX_PAY_H5("WX_PAY_H5", "微信 H5 支付", CapabilityGroup.PAYMENT),
    ;

    private final CapabilityGroup group;

    CapabilityEnum(String key, String desc, CapabilityGroup group) {
        initEnum(key, desc);
        this.group = group;
    }
}
