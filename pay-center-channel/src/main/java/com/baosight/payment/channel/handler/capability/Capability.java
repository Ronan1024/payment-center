package com.baosight.payment.channel.handler.capability;

import com.baosight.payment.channel.enums.ActionCode;
import com.baosight.payment.channel.enums.CapabilityGroup;
import com.baosight.payment.enums.ChannelCode;
import com.baosight.payment.enums.ModeCode;
import com.baosight.payment.enums.PayBrand;
import com.baosight.payment.enums.PayScene;

/**
 * 渠道能力
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
public interface Capability {
    record CapabilityInfo(ActionCode actionCode, PayBrand payBrand, PayScene scene, ModeCode mode,  String name) {}

    /**
     * 接口编号
     */
    String code();

    /**
     * 能力所属渠道
     *
     */
    ChannelCode channel();

    /**
     * 能力域
     */
    CapabilityGroup group();


    /**
     * 能力名称
     */
    String name();

    CapabilityInfo info();


}
