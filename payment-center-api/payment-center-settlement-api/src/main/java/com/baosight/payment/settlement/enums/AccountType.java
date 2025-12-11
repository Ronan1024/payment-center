package com.baosight.payment.settlement.enums;


import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */
public enum AccountType implements IBaseEnum<Integer> {
    /**
     * 支付成功
     */
    SUCCESS(1, "支付成功"),
    /**
     * 提现
     */
    WITHDRAW(2, "提现"),
    ;

    AccountType(Integer type, String msg) {
        initEnum(type, msg);
    }
}
