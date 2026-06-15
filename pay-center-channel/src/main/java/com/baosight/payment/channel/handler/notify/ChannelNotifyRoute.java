package com.baosight.payment.channel.handler.notify;

import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.enums.ChannelCode;
import com.ronan.common.enums.IBaseEnum;

/**
 * 渠道回调路由信息。
 *
 * @param rawChannel  原始渠道路径参数
 * @param rawEvent    原始事件路径参数
 * @param channelCode 解析后的渠道编码
 * @param eventType   解析后的事件类型
 * @author Codex
 * @date 2026/05/13
 */
public record ChannelNotifyRoute(
        String rawChannel,
        String rawEvent,
        ChannelCode channelCode,
        ChannelEventType eventType
) {

    /**
     * 根据回调路径参数构建结构化路由。
     *
     * @param rawChannel 原始渠道路径参数
     * @param rawEvent   原始事件路径参数
     * @return 渠道回调路由
     */
    public static ChannelNotifyRoute parse(String rawChannel, String rawEvent) {
        return new ChannelNotifyRoute(
                rawChannel,
                rawEvent,
                IBaseEnum.getByCode(ChannelCode.class, rawChannel),
                ChannelEventType.fromCode(rawEvent)
        );
    }

    /**
     * 判断是否匹配指定渠道和事件。
     *
     * @param expectedChannel 期望渠道
     * @param expectedEvent   期望事件
     * @return true 表示匹配
     */
    public boolean matches(ChannelCode expectedChannel, ChannelEventType expectedEvent) {
        return channelCode == expectedChannel && eventType == expectedEvent;
    }

    /**
     * 兼容旧版字符串路由 key。
     *
     * @return 旧版路由 key
     */
    public String legacyKey() {
        return channelCode.code() + "_" + eventType.code();
    }
}
