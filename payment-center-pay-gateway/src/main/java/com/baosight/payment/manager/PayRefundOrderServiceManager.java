package com.baosight.payment.manager;

import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
public interface PayRefundOrderServiceManager {
    /**
     * 获取支付退款订单信息
     *
     * @param refundOrderId 退款订单id
     */
    PayRefundOrderVO payRefundOrderInfo(Long refundOrderId);

    /**
     * 更新退款订单状态
     *
     * @param updateRefundOrderState 更新退款订单状态
     */
    boolean updateRefundOrderState(UpdateRefundOrderState updateRefundOrderState);
}
