package com.baosight.payment.adapter.model;

import com.baosight.payment.adapter.enums.AdapterOperation;
import lombok.Data;

import java.util.List;

/**
 * adapterKey 与渠道配置一致性校验请求。
 */
@Data
public class AdapterValidateRequest {
    /**
     * 渠道配置中的渠道编码。
     */
    private String channelCode;
    /**
     * 渠道配置中的签约模式编码。
     */
    private String modeCode;
    /**
     * 渠道配置中的接口编码。
     */
    private String interfaceCode;
    /**
     * 待校验的适配器唯一键。
     */
    private String adapterKey;
    /**
     * 需要适配器具备的能力列表，例如 PAY、REFUND、NOTIFY。
     */
    private List<String> requiredCapabilities;
    /**
     * 需要进一步校验运行状态的操作类型。
     */
    private AdapterOperation operation;
}
