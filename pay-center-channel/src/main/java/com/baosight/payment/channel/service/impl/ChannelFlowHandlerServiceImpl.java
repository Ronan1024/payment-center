package com.baosight.payment.channel.service.impl;

import com.baosight.payment.channel.handler.channelflow.ChannelFlowHandler;
import com.baosight.payment.channel.service.ChannelFlowHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
@Service
@RequiredArgsConstructor
public class ChannelFlowHandlerServiceImpl implements ChannelFlowHandlerService {
    private final ChannelFlowHandler channelFlowHandler;

    /**
     * 执行渠道流程列表
     *
     * @param channelCode 渠道编码
     * @return 渠道流程列表
     */
    @Override
    public Map<String, String> executeList(String channelCode) {
        return channelFlowHandler.executeList(channelCode);
    }
}
