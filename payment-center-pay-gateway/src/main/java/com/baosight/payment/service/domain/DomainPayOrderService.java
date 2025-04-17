package com.baosight.payment.service.domain;

import com.baosight.payment.model.order.UnifiedOrder;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.pojo.vo.UnifiedOrderResponse;

/**
 * 支付订单逻辑处理
 *
 * @author L.J.Ran
 */
public interface DomainPayOrderService {
    /**
     * 统一下单
     *
     * @param unifiedOrder 下单请求
     * @param payOrder     支付订单操作类型
     * @return
     */
    UnifiedOrderResponse unifiedOrder(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder);

    /**
     * 明确支付订单为成功时的逻辑处理(除更新订单其他业务)
     *
     * @param orderVO 订单信息
     */
    void confirmSuccess(OrderVO orderVO);
}
