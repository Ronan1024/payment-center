package com.baosight.payment.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundChannelHandlerResult extends ChannelHandlerResult{

    /**
     * 原订单
     */
    private Long payOrderId;
    /**
     * 渠道订单号
     */
    private String channelOrderNo;

}
