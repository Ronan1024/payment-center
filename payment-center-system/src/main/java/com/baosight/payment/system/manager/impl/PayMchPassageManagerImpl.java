package com.baosight.payment.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.system.manager.PayMchPassageManager;
import com.baosight.payment.system.mapper.PayMchPassageMapper;
import com.baosight.payment.system.pojo.entity.PayMchPassage;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
@Manager
@RequiredArgsConstructor
public class PayMchPassageManagerImpl implements PayMchPassageManager {
    private final PayMchPassageMapper payMchPassageMapper;

    /**
     * 获取商户应用支付通道
     *
     * @param mchId      商户id
     * @param appId      应用id
     * @param payWayCode 支付方式code
     */
    @Override
    public PayMchPassage mchAppPassage(Long mchId, Long appId, String payWayCode) {
        return payMchPassageMapper.selectOne(new LambdaQueryWrapper<PayMchPassage>()
                .eq(PayMchPassage::getMchId, mchId)
                .eq(PayMchPassage::getAppId, appId)
                .eq(PayMchPassage::getPayWayCode, payWayCode)
        );
    }
}
