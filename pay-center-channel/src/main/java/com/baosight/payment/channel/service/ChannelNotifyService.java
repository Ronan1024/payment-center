package com.baosight.payment.channel.service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/21
 */
public interface ChannelNotifyService {
    /**
     * 处理渠道回调入站请求。
     *
     * @param body    原始请求体
     * @param request HTTP 请求
     * @param channel 渠道
     * @param event   事件类型
     * @param bizId   业务ID
     * @return 渠道要求的响应内容
     */
    String handle(String body, HttpServletRequest request, String channel, String event, Long bizId);
}
