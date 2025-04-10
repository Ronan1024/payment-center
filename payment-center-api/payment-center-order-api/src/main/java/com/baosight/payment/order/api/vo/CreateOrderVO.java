package com.baosight.payment.order.api.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/28
 */
@Data
public class CreateOrderVO {
    private Long orderId;

    private String orderNo;
}
