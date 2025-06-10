package com.baosight.payment.notify.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.notify.mapper.PayMchNotifyConfigMapper;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyConfig;
import com.baosight.payment.notify.service.PayMchNotifyConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_notify_config】的数据库操作Service实现
 * @createDate 2025-03-20 20:21:53
 */
@Service
@RequiredArgsConstructor
public class PayMchNotifyConfigServiceImpl extends ServiceImpl<PayMchNotifyConfigMapper, PayMchNotifyConfig> implements PayMchNotifyConfigService {

    private final PayMchNotifyConfigMapper payMchNotifyConfigMapper;

    /**
     * 获取商家的通知地址配置信息
     *
     * @param productType 当前通知的类型
     * @param mchId       商户id
     * @param parentId    上级服务商id
     */
    @Override
    public String getMchNotifyUrl(String productType, Long mchId, Long parentId) {
        // TODO 后续可进行热点数据优化
        PayMchNotifyConfig payMchNotifyConfig = payMchNotifyConfigMapper.selectOne(new LambdaQueryWrapper<PayMchNotifyConfig>()
                .eq(PayMchNotifyConfig::getMchId, mchId)
                .eq(PayMchNotifyConfig::getProductType, productType));

        String result;
        if (!ObjectUtils.isEmpty(payMchNotifyConfig)) {
            result = payMchNotifyConfig.getNotifyUrl();
        } else if (ObjectUtils.isEmpty(payMchNotifyConfig) && !ObjectUtils.isEmpty(parentId)) {
            // 商家通知地址未获取到， 获取全局配置
            PayMchNotifyConfig mchNotifyConfig = payMchNotifyConfigMapper.selectOne(new LambdaQueryWrapper<PayMchNotifyConfig>()
                    .eq(PayMchNotifyConfig::getParentId, parentId)
                    .eq(PayMchNotifyConfig::getGlobalConfig, Boolean.TRUE)
                    .eq(PayMchNotifyConfig::getProductType, productType)
            );

            result = mchNotifyConfig.getNotifyUrl();
        } else {
            result = null;
        }
        return result;
    }
}




