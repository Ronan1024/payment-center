package com.baosight.payment.adapter.model;

import com.baosight.payment.adapter.enums.AdapterEffectScope;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 设置适配器维护状态请求。
 */
@Data
public class AdapterMaintenanceRequest {
    /**
     * 适配器唯一键。
     */
    private String adapterKey;
    /**
     * 维护影响范围。
     */
    private Set<AdapterEffectScope> effectScope;
    /**
     * 维护原因。
     */
    private String reason;
    /**
     * 计划恢复时间。
     */
    private LocalDateTime recoverAt;
    /**
     * 操作人。
     */
    private String operator;
}
