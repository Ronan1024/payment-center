package com.baosight.payment.enums;


import com.ronan.common.enums.IBaseEnum;


public enum PayInterfaceParamType implements IBaseEnum<Integer> {
    /**
     * json
     */
    JSON(1, "json"),
    /**
     * 自定义
     */
    CUSTOM(2, "自定义");;

    PayInterfaceParamType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
