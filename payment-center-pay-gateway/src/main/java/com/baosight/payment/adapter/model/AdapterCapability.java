package com.baosight.payment.adapter.model;

import com.baosight.payment.adapter.enums.AdapterEffectScope;
import com.baosight.payment.adapter.enums.AdapterRegisterStatus;
import com.baosight.payment.adapter.enums.AdapterRuntimeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * 后端已注册通道适配器能力快照。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdapterCapability {
    /**
     * 适配器分组编码，通常与渠道编码一致，例如 mockpay、wxpay、allinpay。
     */
    private String adapterGroupCode;
    /**
     * 适配器分组展示名称。
     */
    private String adapterGroupName;
    /**
     * 支付渠道编码。
     */
    private String channelCode;
    /**
     * 签约模式编码，例如 DIRECT、SERVICE_PROVIDER、SUB_MERCHANT。
     */
    private String modeCode;
    /**
     * 渠道接口编码。
     */
    private String interfaceCode;
    /**
     * 适配器唯一键，格式固定为 channelCode:modeCode:interfaceCode。
     */
    private String adapterKey;
    /**
     * 支付产品编码，例如 MOCK、JSAPI、H5、NATIVE。
     */
    private String payProduct;
    /**
     * 是否支持新支付。
     */
    private boolean supportPay;
    /**
     * 是否支持查单。
     */
    private boolean supportQuery;
    /**
     * 是否支持关单。
     */
    private boolean supportClose;
    /**
     * 是否支持撤销。
     */
    private boolean supportCancel;
    /**
     * 是否支持新退款。
     */
    private boolean supportRefund;
    /**
     * 是否支持退款查询。
     */
    private boolean supportRefundQuery;
    /**
     * 是否支持账单下载。
     */
    private boolean supportDownloadBill;
    /**
     * 是否支持支付回调验签和解析。
     */
    private boolean supportPayNotify;
    /**
     * 是否支持退款回调验签和解析。
     */
    private boolean supportRefundNotify;
    /**
     * 声明该能力的实现类名或适配器名称。
     */
    private String implementClass;
    /**
     * 代码注册状态。
     */
    private AdapterRegisterStatus registerStatus;
    /**
     * 运维运行状态。
     */
    private AdapterRuntimeStatus runtimeStatus;
    /**
     * 当前是否满足注册状态和运行状态，可被新请求调用。
     */
    private boolean callable;
    /**
     * 当前运行控制影响范围；为空表示未限制具体操作。
     */
    private Set<AdapterEffectScope> effectScope;
    /**
     * 禁用、维护或降级原因。
     */
    private String disabledReason;
    /**
     * 能力加载时间。
     */
    private LocalDateTime loadedAt;

    /**
     * 对外统一返回非空影响范围集合，避免调用方重复判空。
     */
    public Set<AdapterEffectScope> getEffectScope() {
        return effectScope == null ? Collections.emptySet() : effectScope;
    }
}
