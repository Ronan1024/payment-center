package com.baosight.payment.system.api;

import com.baosight.payment.api.MchChannelCorrelationApi;
import com.baosight.payment.system.convert.MchChannelCorrelationConvert;
import com.baosight.payment.system.manager.MchChannelCorrelationManager;
import com.baosight.payment.system.pojo.entity.MchChannelCorrelation;
import com.baosight.payment.vo.MchChannelCorrelationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Component
@RequiredArgsConstructor
public class MchChannelCorrelationApiImpl implements MchChannelCorrelationApi {
    private final MchChannelCorrelationManager mchChannelCorrelationManager;

    /**
     * 获取商户指定渠道信息
     *
     * @param mchId 商户id
     * @param type  渠道类型
     */
    @Override
    public MchChannelCorrelationVO mchChannelCorrelation(Long mchId, Integer type) {
        MchChannelCorrelation mchChannelCorrelation = mchChannelCorrelationManager.mchChannelCorrelation(mchId, type);
        return MchChannelCorrelationConvert.INSTANCE.toMchChannelCorrelationVO(mchChannelCorrelation);
    }
}
