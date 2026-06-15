package com.baosight.payment.adapter.spi;

import com.baosight.payment.adapter.model.AdapterCapability;
import com.baosight.payment.adapter.model.AdapterCapabilityQuery;
import com.baosight.payment.adapter.model.AdapterGroupCapability;

import java.util.List;
import java.util.Optional;

/**
 * 支付通道适配器注册中心。
 */
public interface PayChannelAdapterRegistry {

    Optional<AdapterCapability> findCapability(String adapterKey);

    AdapterCapability getRequiredCapability(String adapterKey);

    List<AdapterCapability> listCapabilities(AdapterCapabilityQuery query);

    List<AdapterGroupCapability> listGroups();
}
