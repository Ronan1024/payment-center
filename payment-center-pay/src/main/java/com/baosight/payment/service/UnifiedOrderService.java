package com.baosight.payment.service;

import com.baosight.payment.enums.PayOrderOption;
import com.baosight.payment.model.UnifiedOrder;
import com.baosight.payment.pojo.entity.PayOrder;

/**
 * 统一下单
 */
public interface UnifiedOrderService {
    /**
     * 统一下单
     *
     * @param unifiedOrder 下单请求
     * @param payOrder     支付订单操作类型
     * @return
     */
    Object unifiedOrder(UnifiedOrder unifiedOrder, PayOrder payOrder);
}
