package com.baosight.payment.channel.handler;

import com.baosight.payment.enums.ChannelCode;
import com.baosight.payment.enums.ModeCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 通联服务商渠道处理器
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
@Component
@RequiredArgsConstructor
public class AllInServiceProviderChannelHandler implements ChannelHandler {

    /**
     * 渠道编号
     *
     */
    @Override
    public ChannelCode channelCode() {
        return ChannelCode.ALLIN_PAY;
    }

    /**
     * 处理器名称
     */
    @Override
    public String name() {
        return "通联服务商处理器";
    }

    /**
     * 渠道模式
     */
    @Override
    public ModeCode modeCode() {
        return ModeCode.SERVICE_PROVIDER;
    }

    /**
     * 渠道编号
     *
     */
    @Override
    public String handlerKey() {
        return channelCode().code() + "_" + modeCode().code();
    }
}
