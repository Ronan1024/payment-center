package com.baosight.payment.system.manager;

import com.baosight.payment.system.pojo.entity.MchChannelCorrelation;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
public interface MchChannelCorrelationManager {

    /**
     * 获取商户指定渠道信息
     *
     * @param mchId 商户id
     * @param type  渠道类型
     */
    MchChannelCorrelation mchChannelCorrelation(Long mchId, Integer type);
}
