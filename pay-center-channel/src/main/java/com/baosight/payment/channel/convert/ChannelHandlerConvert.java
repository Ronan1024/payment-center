package com.baosight.payment.channel.convert;

import com.baosight.payment.channel.handler.ChannelHandler;
import com.baosight.payment.channel.handler.capability.Capability;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerCapabilityRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerListRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
@Mapper
public interface ChannelHandlerConvert {
    ChannelHandlerConvert INSTANCE = Mappers.getMapper(ChannelHandlerConvert.class);


    @Mapping(target = "modeName", expression = "java(handler.modeCode().desc())")
    @Mapping(target = "modeCode", expression = "java(handler.modeCode().code())")
    @Mapping(target = "key", expression = "java(handler.handlerKey())")
    @Mapping(target = "handlerNo", expression = "java(handler.handlerKey())")
    @Mapping(target = "handlerName", expression = "java(handler.name())")
    @Mapping(target = "classInfo",expression = "java(handler.getClass().getName())")
    @Mapping(target = "channelName",expression = "java(handler.channelCode().desc())")
    @Mapping(target = "channelCode", expression = "java(handler.channelCode().code())")
    @Mapping(target = "capabilityCount", ignore = true)
    @Mapping(target = "capabilities", ignore = true)
    ChannelHandlerInfoRespDTO toChannelHandlerInfoRespDTO(ChannelHandler handler);

    @Mapping(target = "key", ignore = true)
    @Mapping(target = "staus", ignore = true)
    @Mapping(target = "payBrand", expression = "java(capability.info().payBrand().desc())")
    @Mapping(target = "code", expression = "java(capability.code())")
    @Mapping(target = "action", expression = "java(capability.info().actionCode().desc())")
    @Mapping(target = "scene", expression = "java(capability.info().scene().desc())")
    @Mapping(target = "group", expression = "java(capability.group().desc())")
    @Mapping(target = "name", expression = "java(capability.name())")
    ChannelHandlerCapabilityRespDTO toChannelHandlerCapabilityRespDTO(Capability capability);

    @Mapping(target = "modeName", expression = "java(value.modeCode().desc())")
    @Mapping(target = "modeCode", expression = "java(value.modeCode().code())")
    @Mapping(target = "handlerNo", expression = "java(value.handlerKey())")
    @Mapping(target = "handlerName", expression = "java(value.name())")
    @Mapping(target = "classInfo", expression = "java(value.getClass().getName())")
    @Mapping(target = "channelCode", expression = "java(value.channelCode().code())")
    @Mapping(target = "capabilities", ignore = true)
    ChannelHandlerListRespDTO toChannelHandlerListRespDTO(ChannelHandler value);
}
