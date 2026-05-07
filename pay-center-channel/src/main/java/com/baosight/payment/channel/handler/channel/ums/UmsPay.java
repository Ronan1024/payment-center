package com.baosight.payment.channel.handler.channel.ums;

import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.handler.channel.IChannel;
import com.baosight.payment.enums.PayingAgency;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/10
 */
@Component
public class UmsPay implements IChannel {
    /**
     * 渠道编号
     */
    @Override
    public String channelCode() {
        return ChannelCode.UMS_PAY.code();
    }

    /**
     * 渠道名称
     *
     */
    @Override
    public String channelName() {
        return ChannelCode.UMS_PAY.desc();
    }

    /**
     * 支付机构
     */
    @Override
    public PayingAgency payingAgency() {
        return null;
    }

    /**
     * 支付渠道发起支付处理
     */
    @Override
    public String pay() {
        return "";
    }
}
