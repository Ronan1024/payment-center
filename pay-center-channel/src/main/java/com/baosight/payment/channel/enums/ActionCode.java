package com.baosight.payment.channel.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 行为编号
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
public enum ActionCode implements IBaseEnum<String> {
    /**
     * 支付
     */
    PAY("PAY", "支付"),
    /**
     * 查单
     */
    QUERY("QUERY","查单"),
    ;

    ActionCode(String code, String desc) {
        initEnum(code, desc);
    }
}
