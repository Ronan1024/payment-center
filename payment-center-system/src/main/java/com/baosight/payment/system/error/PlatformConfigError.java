package com.baosight.payment.system.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/10
 */
public enum PlatformConfigError implements IErrorEnum<String> {
    /**
     * 当前配置类型异常
     */
    CONFIG_TYPE_ERROR("800001", "当前配置类型异常"),
    /**
     * 系统配置参数值格式异常
     */
    SYSTEM_PARAM_VALUE_FORMAT_ERROR("800002", "系统配置参数值格式异常"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR("800003", "未知错误");

    PlatformConfigError(String code, String msg) {
        initEnum(code, msg);
    }
}
