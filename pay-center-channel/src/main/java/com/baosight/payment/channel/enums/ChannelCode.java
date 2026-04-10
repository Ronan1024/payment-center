package com.baosight.payment.channel.enums;

import lombok.Getter;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Getter
public enum ChannelCode {

    /**
     * 通联支付
     */
    ALLIN_PAY("ALLIN-PAY", "通联支付"),

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


}
