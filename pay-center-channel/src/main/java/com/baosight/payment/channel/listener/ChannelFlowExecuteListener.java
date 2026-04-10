package com.baosight.payment.channel.listener;

import com.baosight.payment.channel.event.ChannelFlowExecuteListenerEvent;
import com.baosight.payment.channel.handler.channelflow.ChannelFlowHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/8
 */
@Component
@RequiredArgsConstructor
public class ChannelFlowExecuteListener {

    private final ChannelFlowHandler channelFlowHandler;

    @Async
    @EventListener
    public void channelFlowExecute(ChannelFlowExecuteListenerEvent event) {
        channelFlowHandler.execute(event.getChannelCode(), event.getChannelFlowType(),
                event.getClientId(), event.getClientType(), event.getBody());
    }
}
