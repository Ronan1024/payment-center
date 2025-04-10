package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 子订单类型
 * @author: L.J.Ran
 * @create: 2025/4/5
 */
public enum SubOrderType implements IBaseEnum<Integer> {
    /**
     * 消费
     */
    CONSUMPTION(1, "消费", "CONSUMPTION"),
    /**
     * 商户收款
     */
    MERCHANT_RECEIPT(2, "商户收款", "MERCHANT_RECEIPT"),
    /**
     * 预授权 如微信支付的预授权
     */
    PRE_AUTHORIZATION(3, "预授权", "PRE_AUTHORIZATION"),

    ;

    private final String orderType;

    SubOrderType(Integer code, String msg, String orderType) {
        initEnum(code, msg);
        this.orderType = orderType;
    }
}
