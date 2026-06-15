package com.baosight.payment.adapter.service;

import com.baosight.payment.adapter.enums.AdapterEffectScope;
import com.baosight.payment.adapter.enums.AdapterOperation;
import com.baosight.payment.adapter.enums.AdapterRegisterStatus;
import com.baosight.payment.adapter.enums.AdapterRuntimeStatus;
import com.baosight.payment.adapter.model.AdapterCapability;
import com.baosight.payment.adapter.spi.PayChannelAdapterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 适配器运行可用性校验。
 */
@Service
@RequiredArgsConstructor
public class AdapterAvailabilityChecker {

    private final PayChannelAdapterRegistry adapterRegistry;

    /**
     * 校验指定 adapterKey 是否允许执行当前操作。
     *
     * @param adapterKey 适配器唯一键
     * @param operation  当前请求操作
     */
    public void checkAvailable(String adapterKey, AdapterOperation operation) {
        AdapterCapability capability = adapterRegistry.getRequiredCapability(adapterKey);
        if (capability.getRegisterStatus() != AdapterRegisterStatus.REGISTERED) {
            throw new IllegalStateException("适配器未注册: " + adapterKey);
        }
        if (capability.getRuntimeStatus() == AdapterRuntimeStatus.ENABLED) {
            return;
        }
        if (capability.getEffectScope().contains(AdapterEffectScope.ALL)
                || capability.getEffectScope().contains(operation.effectScope())) {
            throw new IllegalStateException("当前操作被运行控制限制: " + adapterKey + ", operation=" + operation);
        }
        if (capability.getRuntimeStatus() == AdapterRuntimeStatus.DISABLED) {
            throw new IllegalStateException("适配器运行状态不允许调用: " + adapterKey);
        }
    }
}
