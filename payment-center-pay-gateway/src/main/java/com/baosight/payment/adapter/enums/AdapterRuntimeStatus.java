package com.baosight.payment.adapter.enums;

import lombok.Getter;

/**
 * 适配器运行启停状态。
 */
@Getter
public enum AdapterRuntimeStatus {
    /**
     * 启用，允许调用。
     */
    ENABLED("启用"),
    /**
     * 禁用，不允许调用。
     */
    DISABLED("禁用"),
    /**
     * 维护中，按影响范围限制调用。
     */
    MAINTENANCE("维护中"),
    /**
     * 降级中，按影响范围限制调用。
     */
    DEGRADED("降级中");

    private final String desc;

    AdapterRuntimeStatus(String desc) {
        this.desc = desc;
    }
}
