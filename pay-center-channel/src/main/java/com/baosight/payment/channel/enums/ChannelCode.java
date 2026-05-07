package com.baosight.payment.channel.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
public enum ChannelCode implements IBaseEnum<String> {

    /**
     * 通联支付
     */
    ALLIN_PAY("ALLIN-PAY", "通联支付"),

    /**
     * 微信支付
     */
    WECHAT_PAY("WECHAT-PAY", "微信支付"),

    /**
     * 银联商务
     */
    UMS_PAY("UMS-PAY", "银联商务"),

    ;


    private final String code;
    private final String channelName;

    ChannelCode(String channelCode, String channelName) {
        this.code = channelCode;
        this.channelName = channelName;
    }

    @Override
    public String code() {
        return this.code;
    }

    public String channelName() {
        return channelName;
    }


}
