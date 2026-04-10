package com.baosight.payment.system.listener;

import com.baosight.payment.channel.event.ChannelFlowExecuteResultListenerEvent;
import com.baosight.payment.system.service.SystemMchChannelConfigFlowService;
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
public class ChannelFlowExecuteResultListener {
    private final SystemMchChannelConfigFlowService systemMchChannelConfigFlowService;


    /**
     * 渠道流程执行结果通知
     *
     * @param event
     */
    @Async
    @EventListener
    public void channelFlowExecuteResult(ChannelFlowExecuteResultListenerEvent event) {
        Boolean flow = systemMchChannelConfigFlowService.updateMchChannelConfigFlow(event);
        if (Boolean.TRUE.equals(flow)) {
            // 查询是否有内部自动化事件
            systemMchChannelConfigFlowService.execute(event.getClientId(), event.getClientType(), event.getChannelCode(), "");
        }
    }

}
