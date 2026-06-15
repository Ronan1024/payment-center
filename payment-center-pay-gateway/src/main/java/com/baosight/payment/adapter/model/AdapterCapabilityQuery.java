package com.baosight.payment.adapter.model;

import com.baosight.payment.adapter.enums.AdapterRegisterStatus;
import com.baosight.payment.adapter.enums.AdapterRuntimeStatus;
import lombok.Data;

/**
 * 适配器能力列表查询条件。
 */
@Data
public class AdapterCapabilityQuery {
    /**
     * 适配器唯一键，精确匹配。
     */
    private String adapterKey;
    /**
     * 支付渠道编码。
     */
    private String channelCode;
    /**
     * 适配器分组编码。
     */
    private String adapterGroupCode;
    /**
     * 签约模式编码。
     */
    private String modeCode;
    /**
     * 渠道接口编码。
     */
    private String interfaceCode;
    /**
     * 支付产品编码。
     */
    private String payProduct;
    /**
     * 代码注册状态。
     */
    private AdapterRegisterStatus registerStatus;
    /**
     * 运维运行状态。
     */
    private AdapterRuntimeStatus runtimeStatus;
    /**
     * 是否当前可调用。
     */
    private Boolean callable;
}
