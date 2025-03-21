package com.baosight.payment.system.error;

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
    PAY_INTERFACE_ISV_PARAMS_NULL("10104", "服务商接口配置定义不能为空"),
    /**
     * 支付接口费率异常
     */
    PAY_INTERFACE_RATE_ERROR("10105", "支付接口费率异常:{}"),
    /**
     * 支付接口不存在或者未启用
     */
    PAY_INTERFACE_DISABLED("10106", "支付接口不存在或者未启用"),
    /**
     * 支付接口更新配置失败
     */
    PAY_INTERFACE_UPDATE_CONFIG_FAIL("10107", "支付接口更新配置失败"),
    /**
     * 当前支付接口不支持服务商配置
     */
    PAY_INTERFACE_NOT_ISV("10108","当前支付接口不支持服务商配置"),

    /**
     * 支付渠道未配置或未启用
     */
    PAY_INTERFACE_CHANNEL_NOT_CONFIG("10109","支付渠道未配置或未启用"),

    /**
     * 服务商暂未开启支付配置
     */
    PAY_INTERFACE_ISV_NOT_ENABLE("10110","服务商暂未开启支付配置"),

    ;

    PayInterfaceError(String code, String message) {
        initEnum(code, message);
    }
}
