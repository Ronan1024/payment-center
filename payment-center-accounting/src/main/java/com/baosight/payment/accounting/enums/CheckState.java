package com.baosight.payment.accounting.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
public enum CheckState implements IBaseEnum<Integer> {
    /**
     * 对平
     */
    BALANCED(1, "对平"),
    /**
     * 渠道多帐
     */
    CHANNEL_OVER(2, "渠道多帐"),
    /**
     * 系统多帐
     */
    SYSTEM_OVER(3, "系统多帐"),

    /**
     * 错帐
     */
    MISTAKE(4, "错帐"),
    ;

    CheckState(Integer code, String msg) {
        initEnum(code, msg);
    }
}
