package com.baosight.payment.channel.pojo.dao;

import lombok.Data;

@Data
public class TongLianMktresultDTO   {

    /**
     * 权益明细id
     */
    private String id;

    /**
     * 权益明细名称，例如券名称、立减活动名称
     */
    private String name;

    /**
     * 权益类型
     * 0-满减券
     * 1-折扣券
     * 2-交易立减
     * 3-礼品卡
     */
    private String tickettype;

    /**
     * 订单优惠金额（单位：分）
     */
    private Long cutoffvalue;

    /**
     * 补贴金额（单位：分）
     * 本权益抵扣总金额中预充值补贴部分结算给商户；
     * 如果非预充值补贴，本字段为0
     */
    private Long perkvalue;
}
