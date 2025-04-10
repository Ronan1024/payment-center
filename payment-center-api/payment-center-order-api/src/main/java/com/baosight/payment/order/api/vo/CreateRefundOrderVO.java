package com.baosight.payment.order.api.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Data
public class CreateRefundOrderVO {
    /**
     * 退款订单id
     */
    private Long refundOrderId;

    /**
     * 退款订单编号
     */
    private String refundOrderNo;
}
