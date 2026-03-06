package com.baosight.payment.channel.dao.manager;

import com.baosight.database.core.annotation.Manager;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.channel.dao.entity.PayingChannelInfo;
import com.baosight.payment.channel.dao.mapper.PayingChannelInfoMapper;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Manager
@RequiredArgsConstructor
public class PayingChannelInfoManager extends BaseManagerImpl<PayingChannelInfoMapper, PayingChannelInfo> {

    /**
     * 根据支付渠道编号获取信息
     * @param code  支付渠道编号
     */
    public PayingChannelInfo infoByCode(String code){
        return this.lambdaQuery().eq(PayingChannelInfo::getChannelCode, code).one();
    }


    /**
     * 保存支付渠道信息
     * @param payingChannelInfo 支付渠道信息
     */
    public Boolean savePayingChannelInfo(PayingChannelInfo payingChannelInfo) {
        return this.save(payingChannelInfo);
    }
}
