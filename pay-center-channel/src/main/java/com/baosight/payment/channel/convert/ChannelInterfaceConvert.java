package com.baosight.payment.channel.convert;

import com.baosight.payment.channel.dao.entity.ChannelInterface;
import com.baosight.payment.channel.handler.capability.Capability;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfaceInfoRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 渠道接口对象转换器。
 *
 * <p>
 * 负责代码能力元数据、渠道接口实体和运营端响应对象之间的转换，
 * 避免在 Service 层散落字段拷贝逻辑。
 * </p>
 *
 * @program: payment-center
 * @description: 渠道接口对象转换
 * @author: L.J.Ran
 * @create: 2026/6/12
 */
@Mapper
public interface ChannelInterfaceConvert {
    ChannelInterfaceConvert INSTANCE = Mappers.getMapper(ChannelInterfaceConvert.class);


    @Mapping(target = "channelCode", ignore = true)
    @Mapping(target = "modeCode", expression = "java(info.mode().code())")
    @Mapping(target = "handlerKey", ignore = true)
    @Mapping(target = "interfaceKey", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "supportTest", ignore = true)
    @Mapping(target = "supportNotify", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "payScene", expression = "java(info.scene().code())")
    @Mapping(target = "payProduct", ignore = true)
    @Mapping(target = "minAmount", ignore = true)
    @Mapping(target = "maxAmount", ignore = true)
    @Mapping(target = "interfaceName", source = "name")
    @Mapping(target = "interfaceCode", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "capabilityType",ignore = true)
    ChannelInterface toChannelInterface(Capability.CapabilityInfo info);

    ChannelInterfaceInfoRespDTO toChannelInterfaceInfoRespDTO(ChannelInterface channelInterface);
}
