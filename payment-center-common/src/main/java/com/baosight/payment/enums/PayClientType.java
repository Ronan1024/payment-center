package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * @author L.J.Rab
 */
public enum PayClientType implements IBaseEnum<Integer> {
    /**
     * 服务商
     */
    SERVICE_PROVIDER(1, "服务商"),
    /**
     * 商家
     */
    MERCHANT(2, "商家"),
    /**
     * 服务商子商家
     */
    SUB_MERCHANT(3, "服务商子商家");;

    PayClientType(Integer code, String msg) {
        initEnum(code, msg);
    }


}
