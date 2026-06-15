package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 支付场景
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/11
 */
public enum PayScene implements IBaseEnum<String> {
    /**
     * H5
     */
    H5("H5", "H5"),
    ;

    PayScene(String code, String msg){
        initEnum(code, msg);
    }
}
