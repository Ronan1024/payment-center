package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 通知类型
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
public enum NotifyType implements IBaseEnum<Integer> {
    /**
     * 支付成功
     */
    PAY_SUCCESS(1, "支付成功"),
    /**
     * 退款成功
     */
    REFUND_SUCCESS(2, "退款成功"),
    /**
     * 提现成功
     */
    WITHDRAW_SUCCESS(3, "提现成功"),
    ;

    NotifyType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
