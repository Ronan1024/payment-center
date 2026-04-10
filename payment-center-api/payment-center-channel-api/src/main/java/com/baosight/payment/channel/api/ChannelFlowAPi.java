package com.baosight.payment.channel.api;

import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
public interface ChannelFlowAPi {

    /**
     * 获取渠道执行流程列表
     */
    Map<String, String> channelFlowList(String channelCode);


}
