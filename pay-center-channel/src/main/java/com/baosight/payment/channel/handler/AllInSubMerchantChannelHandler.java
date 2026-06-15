package com.baosight.payment.channel.handler;

import com.baosight.payment.enums.ChannelCode;
import com.baosight.payment.enums.ModeCode;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/12
 */
@Component
public class AllInSubMerchantChannelHandler implements ChannelHandler {

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
        return "通联子商户处理器";
    }

    /**
     * 渠道模式
     */
    @Override
    public ModeCode modeCode() {
        return ModeCode.SUB_MERCHANT;
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
