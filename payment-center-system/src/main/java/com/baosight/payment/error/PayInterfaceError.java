package com.baosight.payment.error;

import com.baosight.utils.enums.IBaseEnum;

public enum PayInterfaceError implements IBaseEnum<String> {
    /**
     * 支付接口名称已存在
     */
    PAY_INTERFACE_NAME_EXIST("10101", "支付接口名称已存在"),
    /**
     * 支付接口信息不存在
     */
    PAY_INTERFACE_NOT_EXIST("10101", "支付接口信息不存在"),

    /**
     * 普通商户接口配置定义不能为空
     */
    PAY_INTERFACE_NORMAL_MCH_PARAMS_NULL("10102", "普通商户接口配置定义不能为空"),

    /**
     * 特约商户接口配置定义不能为空
     */
    PAY_INTERFACE_ISV_SUB_MCH_PARAMS_NULL("10103", "服务商子商户接口配置定义不能为空"),

    /**
     * 服务商接口配置定义不能为空
     */
    PAY_INTERFACE_ISV_PARAMS_NULL("10104", "服务商接口配置定义不能为空");;

    PayInterfaceError(String code, String message) {
        initEnum(code, message);
    }
}
