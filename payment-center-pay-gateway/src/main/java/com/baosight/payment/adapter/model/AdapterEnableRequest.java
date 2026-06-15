package com.baosight.payment.adapter.model;

import lombok.Data;

/**
 * 启用适配器请求。
 */
@Data
public class AdapterEnableRequest {
    /**
     * 适配器唯一键。
     */
    private String adapterKey;
    /**
     * 恢复原因。
     */
    private String reason;
    /**
     * 操作人。
     */
    private String operator;
}
