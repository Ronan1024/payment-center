package com.baosight.payment.channel.handler;

import com.baosight.payment.enums.ChannelCode;
import com.baosight.payment.enums.ModeCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/21
 */
public interface ChannelHandler {
    /**
     * 渠道编号
     *
     */
    ChannelCode channelCode();

    /**
     * 处理器名称
     */
    String name();

    /**
     * 渠道模式
     */
    ModeCode modeCode();


    /**
     * 处理器标识
     *
     */
    String handlerKey();



}
