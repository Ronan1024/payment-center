package com.baosight.payment.channel.convert;

import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/15
 */
@Mapper
public interface ChannelGatewayLogConvert {
    ChannelGatewayLogConvert INSTANCE = Mappers.getMapper(ChannelGatewayLogConvert.class);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    ChannelGatewayLog toChannelGatewayLog(ChannelGatewayLogDAO channelGatewayLogDAO);
}
