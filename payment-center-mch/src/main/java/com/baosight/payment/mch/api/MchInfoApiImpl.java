package com.baosight.payment.mch.api;

import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.mch.convert.PayMchInfoConvert;
import com.baosight.payment.mch.pojo.entity.PayMchInfo;
import com.baosight.payment.mch.service.PayMchInfoService;
import com.baosight.payment.vo.MchInfoVO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author L.J.Ran
 */
@Component
@RequiredArgsConstructor
public class MchInfoApiImpl implements MchInfoApi {
    @Resource
    private PayMchInfoService payMchInfoService;

    /**
     * 获取商户信息
     *
     * @param mchId 商户id
     */
    @Override
    public MchInfoVO mchInfo(Long mchId) {
        PayMchInfo info = payMchInfoService.infoById(mchId);
        return PayMchInfoConvert.INSTANCE.toMchInfoVO(info);
    }
}
