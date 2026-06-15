package com.baosight.payment.adapter.model;

import com.baosight.payment.adapter.enums.AdapterEffectScope;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 禁用适配器请求。
 */
@Data
public class AdapterDisableRequest {
    /**
     * 适配器唯一键。
     */
    private String adapterKey;
    /**
     * 禁用影响范围。
     */
    private Set<AdapterEffectScope> effectScope;
    /**
     * 禁用原因。
     */
    private String reason;
    /**
     * 计划恢复时间。
     */
    private LocalDateTime recoverAt;
    /**
     * 是否自动恢复。
     */
    private Boolean autoRecover;
    /**
     * 操作人。
     */
    private String operator;
}
