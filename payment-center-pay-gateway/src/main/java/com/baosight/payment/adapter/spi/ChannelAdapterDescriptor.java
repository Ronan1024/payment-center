package com.baosight.payment.adapter.spi;

import com.baosight.payment.adapter.model.AdapterCapability;

/**
 * 现有渠道实现向注册中心声明自身适配器能力。
 */
public interface ChannelAdapterDescriptor {

    AdapterCapability capability();
}
