package com.baosight.payment.order.api;

import com.baosight.payment.order.api.dto.CrCreateOrderDTO;
import com.baosight.payment.order.api.vo.OrderVO;

/**
 * @program: payment-center
 * @description: 订单接口
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
public interface OrderApi {

    /**
     * 码牌支付创建订单
     */
    Boolean qCrCreateOrder(CrCreateOrderDTO crCreateOrderDTO);

    /**
     * 获取订单信息
     * @param orderId 订单id
     */
    OrderVO orderInfo(Long orderId);
}
