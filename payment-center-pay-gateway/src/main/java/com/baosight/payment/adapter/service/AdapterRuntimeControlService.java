package com.baosight.payment.adapter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.adapter.model.AdapterCapability;
import com.baosight.payment.adapter.model.AdapterDisableRequest;
import com.baosight.payment.adapter.model.AdapterEnableRequest;
import com.baosight.payment.adapter.model.AdapterMaintenanceRequest;
import com.baosight.payment.adapter.model.AdapterOperationLogQuery;
import com.baosight.payment.adapter.pojo.entity.PayChannelAdapterControl;
import com.baosight.payment.adapter.pojo.entity.PayChannelAdapterOperationLog;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 适配器运行控制服务。
 */
public interface AdapterRuntimeControlService extends IService<PayChannelAdapterControl> {

    /**
     * 批量查询 adapterKey 对应的运行控制记录。
     */
    Map<String, PayChannelAdapterControl> listControlMap(Collection<String> adapterKeys);

    /**
     * 将运行控制记录合并到能力快照。
     */
    AdapterCapability mergeControl(AdapterCapability capability);

    /**
     * 禁用适配器。
     */
    PayChannelAdapterControl disable(AdapterDisableRequest request, AdapterCapability capability);

    /**
     * 启用适配器。
     */
    PayChannelAdapterControl enable(AdapterEnableRequest request, AdapterCapability capability);

    /**
     * 设置适配器维护状态。
     */
    PayChannelAdapterControl maintenance(AdapterMaintenanceRequest request, AdapterCapability capability);

    /**
     * 查询运行控制操作日志。
     */
    List<PayChannelAdapterOperationLog> operationLogs(AdapterOperationLogQuery query);
}
