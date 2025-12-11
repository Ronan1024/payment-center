package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.enums.State;
import com.baosight.payment.system.pojo.entity.PayMchPassage;
import com.baosight.payment.system.mapper.PayMchPassageMapper;
import com.baosight.payment.system.service.PayMchPassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_passage(商户支付通道表)】的数据库操作Service实现
 * @createDate 2025-03-19 20:46:12
 */
@Service
@RequiredArgsConstructor
public class PayMchPassageServiceImpl extends ServiceImpl<PayMchPassageMapper, PayMchPassage>
        implements PayMchPassageService {
    private final PayMchPassageMapper payMchPassageMapper;

    /**
     * 根据 应用编号及商户编号获取 支付通道信息
     *
     * @param appId 应用id
     * @param mchId 商户id
     */
    @Override
    public List<PayMchPassage> getPayPassageByAppId(Long appId, Long mchId) {
        return payMchPassageMapper.selectList(new LambdaQueryWrapper<PayMchPassage>()
                .eq(PayMchPassage::getMchId, mchId)
                .eq(PayMchPassage::getState, State.NORMAL.code())
                .eq(PayMchPassage::getAppId, appId)
        );
    }
}




