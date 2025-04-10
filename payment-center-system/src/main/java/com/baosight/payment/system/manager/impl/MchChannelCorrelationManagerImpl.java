package com.baosight.payment.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.system.manager.MchChannelCorrelationManager;
import com.baosight.payment.system.mapper.MchChannelCorrelationMapper;
import com.baosight.payment.system.pojo.entity.MchChannelCorrelation;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Manager
@RequiredArgsConstructor
public class MchChannelCorrelationManagerImpl implements MchChannelCorrelationManager {
    private final MchChannelCorrelationMapper mchChannelCorrelationMapper;

    /**
     * 获取商户指定渠道信息
     *
     * @param mchId 商户id
     * @param type  渠道类型
     */
    @Override
    public MchChannelCorrelation mchChannelCorrelation(Long mchId, Integer type) {
        return mchChannelCorrelationMapper.selectOne(new LambdaQueryWrapper<MchChannelCorrelation>()
                .eq(MchChannelCorrelation::getMchId, mchId)
                .eq(MchChannelCorrelation::getType, type)
        );
    }
}
