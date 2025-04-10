package com.baosight.payment.system.api;

import com.baosight.payment.api.AppMchPassageApi;
import com.baosight.payment.system.convert.PayMchPassageConvert;
import com.baosight.payment.system.manager.PayMchPassageManager;
import com.baosight.payment.system.pojo.entity.PayMchPassage;
import com.baosight.payment.vo.MchAppPassageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
@Service
@RequiredArgsConstructor
public class AppMchPassageApiImpl implements AppMchPassageApi {
    private final PayMchPassageManager payMchPassageManager;

    /**
     * 获取商户应用支付通道
     *
     * @param mchId      商户id
     * @param appId      应用id
     * @param payWayCode 支付方式编号
     */
    @Override
    public MchAppPassageVO mchAppPassage(Long mchId, Long appId, String payWayCode) {
        PayMchPassage payMchPassage = payMchPassageManager.mchAppPassage(mchId, appId, payWayCode);
        return PayMchPassageConvert.INSTANCE.toMchAppPassageVO(payMchPassage);
    }
}
