package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * 订单类型
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/4
 */
public enum OrderType implements IBaseEnum<String> {
    /**
     * 消费订单
     */
    CONSUMPTION("1000", "消费订单"),
    /**
     * 退款
     */
    REFUND("1100", "退款订单"),
    /**
     * 提现
     */
    WITHDRAWAL("3000", "提现订单"),
    /**
     * 结算
     */
    SETTLEMENT("4000", "结算订单"),
    /**
     * 差错
     */
    ERROR("5000", "差错订单"),
    ;


    OrderType(String code, String msg) {
        initEnum(code, msg);
    }
}
