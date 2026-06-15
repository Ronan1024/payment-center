package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Getter
public enum ChannelCode implements IBaseEnum<String> {

    /**
     * 通联支付
     */
    ALLIN_PAY("ALLIN-PAY", "通联支付", ChannelType.THIRD_PARTY),

    /**
     * 微信支付
     */
    WECHAT_PAY("WECHAT-PAY", "微信支付", ChannelType.OFFICIAL),

    /**
     * 银联商务
     */
    UMS_PAY("UMS-PAY", "银联商务", ChannelType.THIRD_PARTY),

    ;


    private final ChannelType channelType;


    ChannelCode(String channelCode, String channelName, ChannelType channelType) {
        initEnum(channelCode, channelName);
        this.channelType = channelType;

    }
}
