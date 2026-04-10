package com.baosight.payment.channel.open;

import com.baosight.payment.channel.api.ChannelFlowAPi;
import com.baosight.payment.channel.service.ChannelFlowHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Component
@RequiredArgsConstructor
public class ChannelFlowAPiImpl implements ChannelFlowAPi {

    private final ChannelFlowHandlerService channelFlowHandlerService;


    /**
     * 获取渠道执行流程列表
     *
     * @param channelCode
     */
    @Override
    public Map<String, String> channelFlowList(String channelCode) {
        return channelFlowHandlerService.executeList(channelCode);
    }


}
