package com.baosight.payment.channel.convert;

import com.baosight.payment.channel.dao.entity.PayingChannelInfo;
import com.baosight.payment.channel.dto.resp.ChannelInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.PayingChannelInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Mapper
public interface PayingChannelInfoConvert {
    PayingChannelInfoConvert INSTANCE = Mappers.getMapper(PayingChannelInfoConvert.class);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "channelName", ignore = true)
    PayingChannelInfo toPayingChannelInfo(PayingChannelInfoDTO dto);

    @Mapping(target = "enable", ignore = true)
    ChannelInfoRespDTO toChannelInfoRespDTO(PayingChannelInfo payingChannelInfo);
}
