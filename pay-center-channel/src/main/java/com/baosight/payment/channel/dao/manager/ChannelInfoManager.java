package com.baosight.payment.channel.dao.manager;

import com.baosight.database.core.annotation.Manager;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.channel.dao.entity.ChannelInfo;
import com.baosight.payment.channel.dao.mapper.ChannelInfoMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Manager
@RequiredArgsConstructor
public class ChannelInfoManager extends BaseManagerImpl<ChannelInfoMapper, ChannelInfo> {

    /**
     * 根据支付渠道编号获取信息
     * @param code  支付渠道编号
     */
    public ChannelInfo infoByCode(String code){
        return this.lambdaQuery().eq(ChannelInfo::getChannelCode, code).one();
    }

    /**
     * 根据支付渠道编号获取信息
     * @param codes  支付渠道编号
     */
    public List<ChannelInfo> infoByCode(List<String> codes){
        return this.lambdaQuery().in(ChannelInfo::getChannelCode, codes).list();
    }


    /**
     * 保存支付渠道信息
     * @param payingChannelInfo 支付渠道信息
     */
    public Boolean saveChannelInfo(ChannelInfo payingChannelInfo) {
        return this.save(payingChannelInfo);
    }
}
