package com.baosight.payment.system.manager;

import com.baosight.payment.system.pojo.entity.PayMchPassage;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
public interface PayMchPassageManager {
    /**
     * 获取商户应用支付通道
     *
     * @param mchId      商户id
     * @param appId      应用id
     * @param payWayCode 支付方式code
     */
    PayMchPassage mchAppPassage(Long mchId, Long appId, String payWayCode);
}
