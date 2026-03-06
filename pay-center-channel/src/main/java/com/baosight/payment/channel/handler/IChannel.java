package com.baosight.payment.channel.handler;

import com.baosight.payment.enums.PayingAgency;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
public interface IChannel {

    /**
     * 渠道编号
     */
    String channelCode();

    /**
     * 渠道名称
     *
     */
    String channelName();

    /**
     * 支付机构
     */
    PayingAgency payingAgency();


    /**
     * 支付渠道发起支付处理
     */
    String pay();


    /**
     * 退款
     */

    /**
     * 回调
     */

    /**
     * 订单关闭
     */
}

