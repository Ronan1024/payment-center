package com.baosight.payment.api;

import com.baosight.payment.vo.MchAppPassageVO;

/**
 * @program: payment-center
 * @description: 商户应用支付通道表
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
public interface AppMchPassageApi {

    /**
     * 获取商户应用支付通道
     *
     * @param mchId      商户id
     * @param appId      应用id
     * @param payWayCode 支付方式编号
     */
    MchAppPassageVO mchAppPassage(Long mchId, Long appId, String payWayCode);
}
