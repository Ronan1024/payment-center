package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 交易状态
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/14
 */
public enum TradeState implements IBaseEnum<Integer> {

    /**
     * 初始化
     */
    INIT(0, "初始化"),
    /**
     * 处理中
     */
    PROCESSING(1, "处理中"),
    /**
     * 成功
     */
    SUCCESS(2, "成功"),

    /**
     * 失败
     */
    FAIL(-1, "失败");

    TradeState(Integer code, String msg) {
        initEnum(code, msg);
    }
}
