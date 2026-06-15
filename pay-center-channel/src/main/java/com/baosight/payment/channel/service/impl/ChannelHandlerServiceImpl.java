package com.baosight.payment.channel.service.impl;

import com.baosight.payment.channel.convert.ChannelHandlerConvert;
import com.baosight.payment.channel.enums.CapabilityGroup;
import com.baosight.payment.channel.handler.ChannelCapabilityRegistry;
import com.baosight.payment.channel.handler.ChannelHandler;
import com.baosight.payment.channel.handler.capability.Capability;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerCapabilityRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerListRespDTO;
import com.baosight.payment.channel.service.ChannelHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 渠道处理器管理服务实现。
 */
@Service
@RequiredArgsConstructor
public class ChannelHandlerServiceImpl implements ChannelHandlerService {

    private ChannelCapabilityRegistry channelCapabilityRegistry;


    @Override
    public List<ChannelHandlerListRespDTO> list() {

        List<ChannelHandler> handlerList = channelCapabilityRegistry.channelHandlers();

        return handlerList.stream().map(handler -> {
            ChannelHandlerListRespDTO result = ChannelHandlerConvert.INSTANCE.toChannelHandlerListRespDTO(handler);
            List<Capability> capabilityList = channelCapabilityRegistry.capabilities(handler.handlerKey());
            List<CapabilityGroup> capabilityGroups = capabilityList.stream().map(Capability::group).distinct().toList();
            result.setCapabilities(capabilityGroups);
            return result;
        }).toList();
    }


    /**
     * 查询渠道处理器详情。
     *
     * @param handlerNo 处理器编号
     */
    @Override
    public ChannelHandlerInfoRespDTO info(String handlerNo) {
        ChannelHandler handler = channelCapabilityRegistry.channelHandler(handlerNo);
        ChannelHandlerInfoRespDTO result = ChannelHandlerConvert.INSTANCE.toChannelHandlerInfoRespDTO(handler);
        result.setHandlerNo(handlerNo);
        List<Capability> capabilities = channelCapabilityRegistry.capabilities(handlerNo);
        List<ChannelHandlerCapabilityRespDTO> capabilityRespDTOList = capabilities.stream()
                .map(e -> {
                    ChannelHandlerCapabilityRespDTO channelHandlerCapabilityRespDTO = ChannelHandlerConvert.INSTANCE.toChannelHandlerCapabilityRespDTO(e);
                    channelHandlerCapabilityRespDTO.setKey(channelCapabilityRegistry.buildCapabilityKey(e));
                    return channelHandlerCapabilityRespDTO;
                }).toList();
        result.setCapabilities(capabilityRespDTOList);
        result.setCapabilityCount(capabilityRespDTOList.size());
        return result;
    }

}
