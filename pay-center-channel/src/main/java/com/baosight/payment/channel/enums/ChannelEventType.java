package com.baosight.payment.channel.enums;

import com.baosight.payment.channel.error.ChannelError;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

import java.util.Optional;

/**
 * 渠道事件类型。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Getter
public enum ChannelEventType implements IBaseEnum<String> {

    /**
     * 申请绑定手机号
     */
    BIND_PHONE_REPORT("BIND-PHONE-REPORT", "申请绑定手机号"),

    /**
     * 绑定通联收银宝
     */
    BIND_ALL_IN_SYB("BIND-ALL-IN-SYB", "绑定通联收银宝"),

    /**
     * 会员绑定手机号结果。
     */
    MEMBER_PHONE_BIND("MEMBER_PHONE_BIND", "会员绑定手机号结果"),

    /**
     * 支付订单
     */
    PAY_ORDER("PAY_ORDER", "订单支付"),

    /**
     * 退款结果。
     */
    REFUND("REFUND", "退款结果"),


    /**
     * 协议签订结果。
     */
    AGREEMENT_SIGN("AGREEMENT_SIGN", "协议签订结果");

    ChannelEventType(String code, String description) {
        initEnum(code, description);
    }

    public static ChannelEventType fromCode(String code) {
        ChannelEventType result = IBaseEnum.getByCode(ChannelEventType.class, code);
        return Optional.ofNullable(result).orElseThrow(ApiException.supplier(ChannelError.CHANNEL_CODE_ERROR));
    }
}
