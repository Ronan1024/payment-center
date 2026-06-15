package com.baosight.payment.channel.handler;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.channel.convert.ChannelInterfaceConvert;
import com.baosight.payment.channel.dao.entity.ChannelInterface;
import com.baosight.payment.channel.dao.manager.ChannelInterfaceManager;
import com.baosight.payment.channel.enums.InterfaceStatusEnum;
import com.baosight.payment.channel.handler.capability.Capability;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/12
 */
@Slf4j
@Component
public class ChannelCapabilityRegistry {

    private final ChannelInterfaceManager channelInterfaceManager;


    private final Map<String, ChannelHandler> handlerMap = new ConcurrentHashMap<>();

    private final Map<String, Map<String, Capability>> capabilityMap = new ConcurrentHashMap<>();

    ChannelCapabilityRegistry(List<Capability> capabilities, List<ChannelHandler> channelHandlers, ChannelInterfaceManager channelInterfaceManager) {
        this.channelInterfaceManager = channelInterfaceManager;
        Map<String, String> map = new HashMap<>(capabilities.size());
        for (ChannelHandler channelHandler : channelHandlers) {
            if (handlerMap.containsKey(channelHandler.handlerKey())) {
                throw new ServiceException("渠道处理器 KEY 已存在: " + channelHandler.handlerKey());
            }
            handlerMap.put(channelHandler.handlerKey(), channelHandler);

            List<Capability> list = capabilities.stream().filter(e -> e.channel().equals(channelHandler.channelCode()))
                    .filter(e -> e.info().mode().equals(channelHandler.modeCode()))
                    .toList();
            for (Capability capability : list) {
                String key = buildCapabilityKey(capability);
                if (map.containsKey(key)) {
                    throw new ServiceException("渠道处理器能力 KEY 已存在: " + capability.code());
                }
                map.put(key, channelHandler.handlerKey());
                capabilityMap.computeIfAbsent(channelHandler.handlerKey(), k -> new HashMap<>()).put(key, capability);
            }

        }
    }


    public ChannelHandler channelHandler(String handlerKey) {
        ChannelHandler channelHandler = handlerMap.get(handlerKey);
        if (ObjectUtils.isEmpty(channelHandler)) {
            throw new ServiceException("渠道处理器不存在");
        }

        return channelHandler;
    }


    public List<Capability> capabilities(String handlerKey) {
        return new ArrayList<>(capabilityMap.get(handlerKey).values());
    }


    public List<ChannelHandler> channelHandlers() {
        return new ArrayList<>(handlerMap.values());
    }


    /**
     * 存储写入渠道能力接口
     */
    @Transactional(rollbackFor = Exception.class)
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        try {

            List<ChannelInterface> channelInterfaces = channelInterfaceManager.channelInterfaceList();
            Map<String, ChannelInterface> channelInterfaceMap = channelInterfaces.stream().collect(Collectors.toMap(ChannelInterface::getInterfaceKey, e -> e));
            List<ChannelInterface> data = new ArrayList<>();
            capabilityMap.forEach((channelHandler, v) -> v.forEach((key, capability) -> {
                if (!channelInterfaceMap.containsKey(key)) {
                    ChannelInterface channelInterface = ChannelInterfaceConvert.INSTANCE.toChannelInterface(capability.info());
                    channelInterface.setStatus(InterfaceStatusEnum.OFFLINE.code());
                    channelInterface.setChannelCode(capability.channel().code());
                    channelInterface.setInterfaceCode(capability.code());
                    channelInterface.setInterfaceKey(key);
                    channelInterface.setCapabilityType(capability.group().code());
                    channelInterface.setHandlerKey(channelHandler);
                    data.add(channelInterface);
                }
            }));
            if (!CollectionUtils.isEmpty(data)) {
                channelInterfaceManager.saveBatch(data);
            }
        } catch (Exception e) {
            log.error("渠道接口同步失败", e);
            throw new ServiceException("渠道接口同步失败");
        }
        log.info("完成同步渠道接口能力");


    }


    public String buildCapabilityKey(Capability capability) {
        return String.join(":",
                capability.channel().code(),
                capability.info().mode().code(),
                capability.group().code(),
                capability.info().actionCode().code(),
                capability.code());
    }

}
