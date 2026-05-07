package com.baosight.payment.channel.handler;

import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.enums.ChannelEventType;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/21
 */
public interface BaseChannel {

    /**
     * 渠道编号
     *
     */
    ChannelCode channelCode();

    /**
     * 执行类型
     */
    ChannelEventType eventType();
}
