package com.baosight.payment.api;

import com.baosight.payment.vo.MchChannelCorrelationVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
public interface MchChannelCorrelationApi {

    /**
     * 获取商户指定渠道信息
     *
     * @param mchId 商户id
     * @param type  渠道类型
     */
    MchChannelCorrelationVO mchChannelCorrelation(Long mchId, Integer type);
}
