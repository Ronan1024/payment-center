package com.baosight.payment.order.api.dto;

import lombok.Data;

/**
 * 更新支付订单对账状态
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@Data
public class UpdatePayOrderCheckState {
    /**
     * 订单状态
     */
    private Integer orderState;

    /**
     * 分账状态
     */
    private Integer divisionState;
}
