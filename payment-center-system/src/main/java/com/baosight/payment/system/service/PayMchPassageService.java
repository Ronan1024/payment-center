package com.baosight.payment.system.service;

import com.baosight.payment.system.pojo.entity.PayMchPassage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_passage(商户支付通道表)】的数据库操作Service
 * @createDate 2025-03-19 20:46:12
 */
public interface PayMchPassageService extends IService<PayMchPassage> {

    /**
     * 根据 应用编号及商户编号获取 支付通道信息
     *
     * @param appId 应用id
     * @param mchId 商户id
     */
    List<PayMchPassage> getPayPassageByAppId(Long appId, Long mchId);
}
