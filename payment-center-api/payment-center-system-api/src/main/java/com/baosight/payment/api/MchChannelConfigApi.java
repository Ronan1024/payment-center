package com.baosight.payment.api;

import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
public interface MchChannelConfigApi {

    /**
     * 获取商户渠道配置信息
     *
     * @param mchId       商户id
     * @param channelCode 渠道code
     */
    Map<String, String> mchChannelConfig(Long mchId, String channelCode);
}
