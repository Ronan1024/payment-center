package com.baosight.payment.adapter.enums;

import lombok.Getter;

/**
 * 适配器代码注册状态。
 */
@Getter
public enum AdapterRegisterStatus {
    /**
     * 已注册。
     */
    REGISTERED("已注册"),
    /**
     * 注册异常。
     */
    ERROR("注册异常"),
    /**
     * 代码实现缺失。
     */
    MISSING("代码实现缺失");

    private final String desc;

    AdapterRegisterStatus(String desc) {
        this.desc = desc;
    }
}
