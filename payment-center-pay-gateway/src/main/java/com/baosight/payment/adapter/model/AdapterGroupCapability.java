package com.baosight.payment.adapter.model;

import com.baosight.payment.adapter.enums.AdapterRegisterStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 渠道适配器分组能力汇总。
 */
@Data
@Builder
public class AdapterGroupCapability {
    /**
     * 适配器分组编码。
     */
    private String adapterGroupCode;
    /**
     * 适配器分组展示名称。
     */
    private String adapterGroupName;
    /**
     * 分组所属渠道编码。
     */
    private String channelCode;
    /**
     * 分组内已注册 adapterKey 数量。
     */
    private long adapterCount;
    /**
     * 分组内已实现的签约模式集合。
     */
    private Set<String> modeCodes;
    /**
     * 分组内已实现的支付产品集合。
     */
    private Set<String> payProducts;
    /**
     * 分组能力汇总，key 为能力字段名，value 表示分组内是否至少一个适配器支持。
     */
    private Map<String, Boolean> capabilitySummary;
    /**
     * 分组注册状态汇总。
     */
    private AdapterRegisterStatus registerStatus;
    /**
     * 分组内运行状态集合。
     */
    private List<String> runtimeStatusSummary;
}
