package com.baosight.payment.manager.impl;

import com.baosight.payment.annotation.Manager;
import com.baosight.payment.manager.PayRefundOrderServiceManager;
import com.baosight.payment.order.api.RefundOrderApi;
import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Manager
@RequiredArgsConstructor
public class PayRefundOrderServiceManagerImpl implements PayRefundOrderServiceManager {
    private final RefundOrderApi refundOrderApi;

    /**
     * 获取支付退款订单信息
     *
     * @param refundOrderId 退款订单id
     */
    @Override
    public PayRefundOrderVO payRefundOrderInfo(Long refundOrderId) {
        return refundOrderApi.refundOrderInfo(refundOrderId);
    }

    /**
     * 更新退款订单状态
     *
     * @param updateRefundOrderState 更新退款订单状态
     */
    @Override
    public boolean updateRefundOrderState(UpdateRefundOrderState updateRefundOrderState) {
        return refundOrderApi.updateInitOrderStateThrowException(updateRefundOrderState);
    }

}
