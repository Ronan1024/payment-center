package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/28
 */
@Getter
public enum PayOrderState implements IBaseEnum<Integer> {
    /**
     * 初始化
     */
    INIT(0, "初始化", "INIT"),

    /**
     * 支付中
     */
    PAYING(1, "支付中", "PAYING"),

    /**
     * 预消费
     */
    PRE_CONSUMPTION(3, "预消费", "PRE_CONSUMPTION"),

    /**
     * 支付成功 预消费成功
     */
    SUCCESS(2, "支付成功", "SUCCESS"),

    /**
     * 支付失败
     */
    FAIL(-1, "支付失败", "FAIL"),
    ;

    private final String stateCode;

    PayOrderState(Integer code, String msg, String stateCode) {
        initEnum(code, msg);
        this.stateCode = stateCode;
    }
}
