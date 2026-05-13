package com.baosight.payment.channel.enums;

import com.baosight.payment.channel.error.ChannelError;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.enums.IBaseEnum;

import java.util.Optional;

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


    ChannelCode(String channelCode, String channelName) {
        initEnum(channelCode, channelName);
    }


    public static ChannelCode fromCode(String code) {
        ChannelCode result = IBaseEnum.getByCode(ChannelCode.class, code);
        return Optional.ofNullable(result).orElseThrow(ApiException.supplier(ChannelError.CHANNEL_CODE_ERROR));
    }
}
