package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 商户类型
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
public enum MchType implements IBaseEnum<Integer> {
    /**
     * 普通商户
     */
    MERCHANT(2, "普通商户"),
    /**
     * 特约商户
     */
    SUB_MERCHANT(3, "特约商户");

    MchType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
