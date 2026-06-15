package com.baosight.payment.adapter.config;

import com.baosight.payment.adapter.model.AdapterCapability;
import com.baosight.payment.adapter.spi.ChannelAdapterDescriptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mockpay 适配器能力声明。
 *
 * <p>当前仅用于适配器能力展示和 adapterKey 校验，不接管真实支付、退款和回调流程。</p>
 */
@Configuration
public class MockpayAdapterDescriptorConfig {

    /**
     * 注册 mockpay 默认直连适配器能力。
     */
    @Bean
    public ChannelAdapterDescriptor mockpayDefaultAdapterDescriptor() {
        return () -> AdapterCapability.builder()
                .adapterGroupCode("mockpay")
                .adapterGroupName("模拟支付适配器分组")
                .channelCode("mockpay")
                .modeCode("DIRECT")
                .interfaceCode("mockpay_default")
                .payProduct("MOCK")
                .supportPay(true)
                .supportQuery(true)
                .supportClose(true)
                .supportCancel(false)
                .supportRefund(true)
                .supportRefundQuery(true)
                .supportDownloadBill(false)
                .supportPayNotify(true)
                .supportRefundNotify(true)
                .implementClass("MockpayDefaultAdapter")
                .build();
    }
}
