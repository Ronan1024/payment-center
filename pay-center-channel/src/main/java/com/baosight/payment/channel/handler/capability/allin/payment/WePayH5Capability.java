package com.baosight.payment.channel.handler.capability.allin.payment;

import com.baosight.payment.channel.enums.ActionCode;
import com.baosight.payment.channel.enums.CapabilityGroup;
import com.baosight.payment.channel.handler.capability.Capability;
import com.baosight.payment.enums.ChannelCode;
import com.baosight.payment.enums.ModeCode;
import com.baosight.payment.enums.PayBrand;
import com.baosight.payment.enums.PayScene;
import org.springframework.stereotype.Component;

/**
 * 微信 H5 支付能力
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
@Component
public class WePayH5Capability implements Capability {
    /**
     * 接口编号
     */
    @Override
    public String code() {
        return "WECHAT_H5_PAY";
    }

    /**
     * 能力所属渠道
     *
     */
    @Override
    public ChannelCode channel() {
        return ChannelCode.ALLIN_PAY;
    }


    @Override
    public CapabilityGroup group() {
        return CapabilityGroup.PAYMENT;
    }


    /**
     * 能力名称
     */
    @Override
    public String name() {
        return "H5 支付";
    }

    @Override
    public CapabilityInfo info() {
     return    new CapabilityInfo(ActionCode.PAY, PayBrand.WECHAT, PayScene.H5, ModeCode.SERVICE_PROVIDER, name() );
    }

}
