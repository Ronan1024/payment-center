package com.baosight.payment.adapter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.adapter.enums.AdapterEffectScope;
import com.baosight.payment.adapter.enums.AdapterRegisterStatus;
import com.baosight.payment.adapter.enums.AdapterRuntimeStatus;
import com.baosight.payment.adapter.mapper.PayChannelAdapterControlMapper;
import com.baosight.payment.adapter.mapper.PayChannelAdapterOperationLogMapper;
import com.baosight.payment.adapter.model.AdapterCapability;
import com.baosight.payment.adapter.model.AdapterDisableRequest;
import com.baosight.payment.adapter.model.AdapterEnableRequest;
import com.baosight.payment.adapter.model.AdapterMaintenanceRequest;
import com.baosight.payment.adapter.model.AdapterOperationLogQuery;
import com.baosight.payment.adapter.pojo.entity.PayChannelAdapterControl;
import com.baosight.payment.adapter.pojo.entity.PayChannelAdapterOperationLog;
import com.baosight.payment.adapter.service.AdapterRuntimeControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 适配器运行控制服务实现。
 */
@Service
@RequiredArgsConstructor
public class AdapterRuntimeControlServiceImpl extends ServiceImpl<PayChannelAdapterControlMapper, PayChannelAdapterControl>
        implements AdapterRuntimeControlService {

    private final PayChannelAdapterOperationLogMapper operationLogMapper;

    @Override
    public Map<String, PayChannelAdapterControl> listControlMap(Collection<String> adapterKeys) {
        if (CollectionUtils.isEmpty(adapterKeys)) {
            return Collections.emptyMap();
        }
        return lambdaQuery()
                .in(PayChannelAdapterControl::getAdapterKey, adapterKeys)
                .list()
                .stream()
                .collect(Collectors.toMap(PayChannelAdapterControl::getAdapterKey, control -> control, (left, right) -> right));
    }

    @Override
    public AdapterCapability mergeControl(AdapterCapability capability) {
        if (capability == null) {
            return null;
        }
        PayChannelAdapterControl control = lambdaQuery()
                .eq(PayChannelAdapterControl::getAdapterKey, capability.getAdapterKey())
                .one();
        if (control == null) {
            return capability;
        }
        mergeControl(capability, control);
        return capability;
    }

    @Override
    public PayChannelAdapterControl disable(AdapterDisableRequest request, AdapterCapability capability) {
        requireText(request.getAdapterKey(), "adapterKey 不能为空");
        requireText(request.getReason(), "禁用原因不能为空");
        Set<AdapterEffectScope> effectScope = CollectionUtils.isEmpty(request.getEffectScope())
                ? Set.of(AdapterEffectScope.PAY)
                : request.getEffectScope();
        PayChannelAdapterControl control = buildControl(capability, AdapterRuntimeStatus.DISABLED, effectScope,
                request.getReason(), request.getOperator(), request.getRecoverAt(), request.getAutoRecover());
        saveOrUpdateByAdapterKey(control);
        saveOperationLog(control, "DISABLE", request.getReason(), request.getOperator());
        return control;
    }

    @Override
    public PayChannelAdapterControl enable(AdapterEnableRequest request, AdapterCapability capability) {
        requireText(request.getAdapterKey(), "adapterKey 不能为空");
        requireText(request.getReason(), "恢复原因不能为空");
        PayChannelAdapterControl control = buildControl(capability, AdapterRuntimeStatus.ENABLED, Set.of(),
                request.getReason(), request.getOperator(), null, Boolean.FALSE);
        saveOrUpdateByAdapterKey(control);
        saveOperationLog(control, "ENABLE", request.getReason(), request.getOperator());
        return control;
    }

    @Override
    public PayChannelAdapterControl maintenance(AdapterMaintenanceRequest request, AdapterCapability capability) {
        requireText(request.getAdapterKey(), "adapterKey 不能为空");
        requireText(request.getReason(), "维护原因不能为空");
        Set<AdapterEffectScope> effectScope = CollectionUtils.isEmpty(request.getEffectScope())
                ? Set.of(AdapterEffectScope.ALL)
                : request.getEffectScope();
        PayChannelAdapterControl control = buildControl(capability, AdapterRuntimeStatus.MAINTENANCE, effectScope,
                request.getReason(), request.getOperator(), request.getRecoverAt(), Boolean.FALSE);
        saveOrUpdateByAdapterKey(control);
        saveOperationLog(control, "MAINTENANCE", request.getReason(), request.getOperator());
        return control;
    }

    @Override
    public List<PayChannelAdapterOperationLog> operationLogs(AdapterOperationLogQuery query) {
        QueryWrapper<PayChannelAdapterOperationLog> wrapper = new QueryWrapper<>();
        if (query != null) {
            wrapper.eq(StringUtils.hasText(query.getAdapterKey()), "adapter_key", query.getAdapterKey())
                    .eq(StringUtils.hasText(query.getOperationType()), "operation_type", query.getOperationType())
                    .eq(StringUtils.hasText(query.getOperator()), "operator_name", query.getOperator())
                    .ge(query.getStartTime() != null, "create_time", query.getStartTime())
                    .le(query.getEndTime() != null, "create_time", query.getEndTime());
        }
        wrapper.orderByDesc("create_time");
        return operationLogMapper.selectList(wrapper);
    }

    private PayChannelAdapterControl buildControl(AdapterCapability capability, AdapterRuntimeStatus runtimeStatus,
                                                  Set<AdapterEffectScope> effectScope, String reason, String operator,
                                                  LocalDateTime recoverAt, Boolean autoRecover) {
        PayChannelAdapterControl control = lambdaQuery()
                .eq(PayChannelAdapterControl::getAdapterKey, capability.getAdapterKey())
                .one();
        if (control == null) {
            control = new PayChannelAdapterControl();
        }
        control.setAdapterKey(capability.getAdapterKey());
        control.setChannelCode(capability.getChannelCode());
        control.setModeCode(capability.getModeCode());
        control.setInterfaceCode(capability.getInterfaceCode());
        control.setRuntimeStatus(runtimeStatus.name());
        control.setEffectScope(joinEffectScope(effectScope));
        control.setDisabledReason(reason);
        control.setDisabledBy(operator);
        control.setDisabledAt(LocalDateTime.now());
        control.setRecoverAt(recoverAt);
        control.setAutoRecover(Boolean.TRUE.equals(autoRecover));
        control.setVersion(control.getVersion() == null ? 1 : control.getVersion() + 1);
        return control;
    }

    private void saveOrUpdateByAdapterKey(PayChannelAdapterControl control) {
        saveOrUpdate(control);
    }

    private void saveOperationLog(PayChannelAdapterControl control, String operationType, String reason, String operator) {
        PayChannelAdapterOperationLog log = new PayChannelAdapterOperationLog();
        log.setAdapterKey(control.getAdapterKey());
        log.setOperationType(operationType);
        log.setRuntimeStatus(control.getRuntimeStatus());
        log.setEffectScope(control.getEffectScope());
        log.setReason(reason);
        log.setOperator(operator);
        operationLogMapper.insert(log);
    }

    private void mergeControl(AdapterCapability capability, PayChannelAdapterControl control) {
        AdapterRuntimeStatus runtimeStatus = parseRuntimeStatus(control.getRuntimeStatus());
        capability.setRuntimeStatus(runtimeStatus);
        capability.setEffectScope(parseEffectScope(control.getEffectScope()));
        capability.setDisabledReason(control.getDisabledReason());
        capability.setCallable(capability.getRegisterStatus() == AdapterRegisterStatus.REGISTERED
                && runtimeStatus == AdapterRuntimeStatus.ENABLED);
    }

    private AdapterRuntimeStatus parseRuntimeStatus(String runtimeStatus) {
        if (!StringUtils.hasText(runtimeStatus)) {
            return AdapterRuntimeStatus.ENABLED;
        }
        return AdapterRuntimeStatus.valueOf(runtimeStatus);
    }

    private Set<AdapterEffectScope> parseEffectScope(String effectScope) {
        if (!StringUtils.hasText(effectScope)) {
            return Set.of();
        }
        return Arrays.stream(effectScope.split(","))
                .filter(StringUtils::hasText)
                .map(AdapterEffectScope::valueOf)
                .collect(Collectors.toSet());
    }

    private String joinEffectScope(Set<AdapterEffectScope> effectScope) {
        if (CollectionUtils.isEmpty(effectScope)) {
            return "";
        }
        return effectScope.stream()
                .filter(Objects::nonNull)
                .map(AdapterEffectScope::name)
                .sorted()
                .collect(Collectors.joining(","));
    }

    private void requireText(String value, String message) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(message);
        }
    }
}
