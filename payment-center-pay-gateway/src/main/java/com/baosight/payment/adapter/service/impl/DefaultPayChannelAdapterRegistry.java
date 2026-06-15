package com.baosight.payment.adapter.service.impl;

import com.baosight.payment.adapter.enums.AdapterRegisterStatus;
import com.baosight.payment.adapter.enums.AdapterRuntimeStatus;
import com.baosight.payment.adapter.model.AdapterCapability;
import com.baosight.payment.adapter.model.AdapterCapabilityQuery;
import com.baosight.payment.adapter.model.AdapterGroupCapability;
import com.baosight.payment.adapter.service.AdapterRuntimeControlService;
import com.baosight.payment.adapter.spi.ChannelAdapterDescriptor;
import com.baosight.payment.adapter.spi.PayChannelAdapterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 默认适配器注册中心，聚合当前 Spring 容器内声明的适配器能力。
 */
@Service
public class DefaultPayChannelAdapterRegistry implements PayChannelAdapterRegistry {

    /**
     * adapterKey 到适配器能力的只读索引。
     */
    private final Map<String, AdapterCapability> capabilityMap;
    /**
     * 运行控制服务；单元测试可为空，生产运行由 Spring 注入。
     */
    private final AdapterRuntimeControlService runtimeControlService;

    /**
     * 构造注册中心时一次性校验并装载所有能力声明。
     */
    public DefaultPayChannelAdapterRegistry(List<ChannelAdapterDescriptor> descriptors) {
        this(descriptors, null);
    }

    /**
     * 构造注册中心时一次性校验并装载所有能力声明，同时注入运行控制服务。
     */
    @Autowired
    public DefaultPayChannelAdapterRegistry(List<ChannelAdapterDescriptor> descriptors,
                                            AdapterRuntimeControlService runtimeControlService) {
        Map<String, AdapterCapability> map = new LinkedHashMap<>();
        for (ChannelAdapterDescriptor descriptor : descriptors) {
            AdapterCapability capability = normalize(descriptor.capability());
            validate(capability);
            if (map.containsKey(capability.getAdapterKey())) {
                throw new IllegalStateException("adapterKey 重复: " + capability.getAdapterKey());
            }
            map.put(capability.getAdapterKey(), capability);
        }
        this.capabilityMap = Map.copyOf(map);
        this.runtimeControlService = runtimeControlService;
    }

    /**
     * 按 adapterKey 查询适配器能力。
     */
    @Override
    public Optional<AdapterCapability> findCapability(String adapterKey) {
        return Optional.ofNullable(capabilityMap.get(adapterKey))
                .map(this::copyCapability)
                .map(this::mergeRuntimeControl);
    }

    /**
     * 按 adapterKey 查询适配器能力，不存在时直接抛出明确异常。
     */
    @Override
    public AdapterCapability getRequiredCapability(String adapterKey) {
        return findCapability(adapterKey)
                .orElseThrow(() -> new IllegalArgumentException("adapterKey 不存在: " + adapterKey));
    }

    /**
     * 按管理端查询条件过滤适配器能力。
     */
    @Override
    public List<AdapterCapability> listCapabilities(AdapterCapabilityQuery query) {
        Predicate<AdapterCapability> predicate = capability -> true;
        if (query != null) {
            predicate = predicate
                    .and(matchString(query.getAdapterKey(), AdapterCapability::getAdapterKey))
                    .and(matchString(query.getChannelCode(), AdapterCapability::getChannelCode))
                    .and(matchString(query.getAdapterGroupCode(), AdapterCapability::getAdapterGroupCode))
                    .and(matchString(query.getModeCode(), AdapterCapability::getModeCode))
                    .and(matchString(query.getInterfaceCode(), AdapterCapability::getInterfaceCode))
                    .and(matchString(query.getPayProduct(), AdapterCapability::getPayProduct))
                    .and(capability -> query.getRegisterStatus() == null || query.getRegisterStatus() == capability.getRegisterStatus())
                    .and(capability -> query.getRuntimeStatus() == null || query.getRuntimeStatus() == capability.getRuntimeStatus())
                    .and(capability -> query.getCallable() == null || query.getCallable().equals(capability.isCallable()));
        }
        return capabilityMap.values().stream()
                .map(this::copyCapability)
                .map(this::mergeRuntimeControl)
                .filter(predicate)
                .sorted(Comparator.comparing(AdapterCapability::getAdapterKey))
                .collect(Collectors.toList());
    }

    /**
     * 按适配器分组聚合能力摘要。
     */
    @Override
    public List<AdapterGroupCapability> listGroups() {
        return listCapabilities(null).stream()
                .collect(Collectors.groupingBy(AdapterCapability::getAdapterGroupCode, LinkedHashMap::new, Collectors.toList()))
                .values()
                .stream()
                .map(this::toGroupCapability)
                .sorted(Comparator.comparing(AdapterGroupCapability::getAdapterGroupCode))
                .collect(Collectors.toList());
    }

    private AdapterCapability normalize(AdapterCapability capability) {
        if (capability == null) {
            throw new IllegalStateException("适配器能力注册失败: capability 不能为空");
        }
        capability.setAdapterKey(capability.getChannelCode() + ":" + capability.getModeCode() + ":" + capability.getInterfaceCode());
        if (capability.getRegisterStatus() == null) {
            capability.setRegisterStatus(AdapterRegisterStatus.REGISTERED);
        }
        if (capability.getRuntimeStatus() == null) {
            capability.setRuntimeStatus(AdapterRuntimeStatus.ENABLED);
        }
        if (capability.getEffectScope() == null) {
            capability.setEffectScope(Set.of());
        }
        if (capability.getLoadedAt() == null) {
            capability.setLoadedAt(LocalDateTime.now());
        }
        capability.setCallable(capability.getRegisterStatus() == AdapterRegisterStatus.REGISTERED
                && capability.getRuntimeStatus() == AdapterRuntimeStatus.ENABLED);
        return capability;
    }

    private void validate(AdapterCapability capability) {
        List<String> errors = new ArrayList<>();
        requireText(errors, capability.getAdapterGroupCode(), "adapterGroupCode");
        requireText(errors, capability.getChannelCode(), "channelCode");
        requireText(errors, capability.getModeCode(), "modeCode");
        requireText(errors, capability.getInterfaceCode(), "interfaceCode");
        requireText(errors, capability.getAdapterKey(), "adapterKey");
        requireText(errors, capability.getPayProduct(), "payProduct");
        requireText(errors, capability.getImplementClass(), "implementClass");
        if (!errors.isEmpty()) {
            throw new IllegalStateException("适配器能力注册失败: " + String.join(", ", errors));
        }
    }

    private void requireText(List<String> errors, String value, String fieldName) {
        if (!StringUtils.hasText(value)) {
            errors.add(fieldName + " 不能为空");
        }
    }

    private Predicate<AdapterCapability> matchString(String expected, Function<AdapterCapability, String> getter) {
        if (!StringUtils.hasText(expected)) {
            return capability -> true;
        }
        return capability -> expected.equals(getter.apply(capability));
    }

    private AdapterGroupCapability toGroupCapability(List<AdapterCapability> capabilities) {
        AdapterCapability first = capabilities.get(0);
        Map<String, Boolean> summary = new LinkedHashMap<>();
        summary.put("supportPay", capabilities.stream().anyMatch(AdapterCapability::isSupportPay));
        summary.put("supportQuery", capabilities.stream().anyMatch(AdapterCapability::isSupportQuery));
        summary.put("supportClose", capabilities.stream().anyMatch(AdapterCapability::isSupportClose));
        summary.put("supportRefund", capabilities.stream().anyMatch(AdapterCapability::isSupportRefund));
        summary.put("supportRefundQuery", capabilities.stream().anyMatch(AdapterCapability::isSupportRefundQuery));
        summary.put("supportDownloadBill", capabilities.stream().anyMatch(AdapterCapability::isSupportDownloadBill));
        summary.put("supportPayNotify", capabilities.stream().anyMatch(AdapterCapability::isSupportPayNotify));
        summary.put("supportRefundNotify", capabilities.stream().anyMatch(AdapterCapability::isSupportRefundNotify));
        return AdapterGroupCapability.builder()
                .adapterGroupCode(first.getAdapterGroupCode())
                .adapterGroupName(first.getAdapterGroupName())
                .channelCode(first.getChannelCode())
                .adapterCount(capabilities.size())
                .modeCodes(capabilities.stream().map(AdapterCapability::getModeCode).collect(Collectors.toSet()))
                .payProducts(capabilities.stream().map(AdapterCapability::getPayProduct).collect(Collectors.toSet()))
                .capabilitySummary(summary)
                .registerStatus(capabilities.stream().anyMatch(c -> c.getRegisterStatus() == AdapterRegisterStatus.ERROR)
                        ? AdapterRegisterStatus.ERROR
                        : AdapterRegisterStatus.REGISTERED)
                .runtimeStatusSummary(capabilities.stream()
                        .map(c -> c.getRuntimeStatus().name())
                        .distinct()
                        .collect(Collectors.toList()))
                .build();
    }

    private AdapterCapability mergeRuntimeControl(AdapterCapability capability) {
        if (runtimeControlService == null) {
            return capability;
        }
        return runtimeControlService.mergeControl(capability);
    }

    private AdapterCapability copyCapability(AdapterCapability source) {
        return AdapterCapability.builder()
                .adapterGroupCode(source.getAdapterGroupCode())
                .adapterGroupName(source.getAdapterGroupName())
                .channelCode(source.getChannelCode())
                .modeCode(source.getModeCode())
                .interfaceCode(source.getInterfaceCode())
                .adapterKey(source.getAdapterKey())
                .payProduct(source.getPayProduct())
                .supportPay(source.isSupportPay())
                .supportQuery(source.isSupportQuery())
                .supportClose(source.isSupportClose())
                .supportCancel(source.isSupportCancel())
                .supportRefund(source.isSupportRefund())
                .supportRefundQuery(source.isSupportRefundQuery())
                .supportDownloadBill(source.isSupportDownloadBill())
                .supportPayNotify(source.isSupportPayNotify())
                .supportRefundNotify(source.isSupportRefundNotify())
                .implementClass(source.getImplementClass())
                .registerStatus(source.getRegisterStatus())
                .runtimeStatus(source.getRuntimeStatus())
                .callable(source.isCallable())
                .effectScope(source.getEffectScope())
                .disabledReason(source.getDisabledReason())
                .loadedAt(source.getLoadedAt())
                .build();
    }
}
