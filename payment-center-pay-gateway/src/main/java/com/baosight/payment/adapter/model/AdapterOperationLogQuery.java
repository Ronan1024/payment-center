package com.baosight.payment.adapter.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 适配器运行控制操作日志查询条件。
 */
@Data
public class AdapterOperationLogQuery {
    /**
     * 适配器唯一键。
     */
    private String adapterKey;
    /**
     * 操作类型：DISABLE、ENABLE、MAINTENANCE。
     */
    private String operationType;
    /**
     * 操作人。
     */
    private String operator;
    /**
     * 操作开始时间。
     */
    private LocalDateTime startTime;
    /**
     * 操作结束时间。
     */
    private LocalDateTime endTime;
}
