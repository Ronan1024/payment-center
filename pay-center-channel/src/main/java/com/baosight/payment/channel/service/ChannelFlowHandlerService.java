package com.baosight.payment.channel.service;

import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
public interface ChannelFlowHandlerService {

    /**
     * 执行渠道流程列表
     * @param channelCode 渠道编码
     * @return 渠道流程列表
     */
    Map<String, String> executeList(String channelCode);
}
