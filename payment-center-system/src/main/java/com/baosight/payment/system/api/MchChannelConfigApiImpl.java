package com.baosight.payment.system.api;

import com.baosight.payment.api.MchChannelConfigApi;
import com.baosight.payment.system.service.SystemMchChannelConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 商户渠道配置信息
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
@Component
@RequiredArgsConstructor
public class MchChannelConfigApiImpl implements MchChannelConfigApi {

    private final SystemMchChannelConfigService systemMchChannelConfigService;

    /**
     * 获取商户渠道配置信息
     *
     * @param mchId       商户id
     * @param channelCode 渠道code
     */
    @Override
    public Map<String, String> mchChannelConfig(Long mchId, String channelCode) {
        return systemMchChannelConfigService.mchChannelConfig(mchId, channelCode);
    }
}
