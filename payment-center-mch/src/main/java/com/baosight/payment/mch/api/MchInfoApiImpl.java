package com.baosight.payment.mch.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.mch.convert.PayMchInfoConvert;
import com.baosight.payment.mch.dao.entity.PayMchInfo;
import com.baosight.payment.mch.service.PayMchInfoService;
import com.baosight.payment.vo.MchInfoVO;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    /**
     * 获取商户信息
     *
     * @param mchNo
     * @return
     */
    @Override
    public MchInfoVO mchInfoBuMchNO(String mchNo) {
        PayMchInfo info = payMchInfoService.infoByMchNo(mchNo);
        return PayMchInfoConvert.INSTANCE.toMchInfoVO(info);
    }

    /**
     * 获取商户信息
     *
     * @param isvId
     * @return
     */
    @Override
    public List<Long> mchInfoByIsvId(Long isvId) {
       return payMchInfoService.list(new LambdaQueryWrapper<PayMchInfo>().eq(PayMchInfo::getIsvId,isvId)).stream().map(PayMchInfo::getId).toList();
    }
}
