package com.baosight.payment.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundOrderChannelHandlerResult extends ChannelHandlerResult {
    /**
     * 支付订单状态
     */
    private Integer payOrderState;
    /**
     * 渠道订单号
     */
    private String channelOrderNo;


}
