package com.baosight.payment.channel.dao.manager;


import com.baosight.database.core.annotation.Manager;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.channel.convert.ChannelGatewayLogConvert;
import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.dao.mapper.ChannelGatewayLogMapper;
import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/15
 */
@Manager
@RequiredArgsConstructor
public class ChannelGatewayLogManager extends BaseManagerImpl<ChannelGatewayLogMapper, ChannelGatewayLog> {

    private final ChannelGatewayLogMapper channelGatewayLogMapper;


    public void saveChannelGatewayLog(ChannelGatewayLogDAO channelGatewayLogDAO) {
        ChannelGatewayLog channelGatewayLog = ChannelGatewayLogConvert.INSTANCE.toChannelGatewayLog(channelGatewayLogDAO);
        channelGatewayLogMapper.insert(channelGatewayLog);

    }
}
