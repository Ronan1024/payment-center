package com.baosight.payment.notify.service;

import com.baosight.payment.notify.pojo.entity.PayMchNotifyConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_notify_config】的数据库操作Service
 * @createDate 2025-03-20 20:21:53
 */
public interface PayMchNotifyConfigService extends IService<PayMchNotifyConfig> {

    /**
     * 获取商家的通知地址配置信息
     *
     * @param productType 产品类型
     * @param mchId       商户id
     * @param parentId       上级id
     */
    String getMchNotifyUrl(String productType, Long mchId, Long parentId);
}
