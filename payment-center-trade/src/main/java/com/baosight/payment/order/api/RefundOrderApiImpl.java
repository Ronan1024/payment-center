package com.baosight.payment.order.api;

import com.baosight.payment.order.api.dto.CreateRefundOrderDTO;
import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.CreateRefundOrderVO;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;
import com.baosight.payment.order.manager.PayRefundOrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Component
@RequiredArgsConstructor
public class RefundOrderApiImpl implements RefundOrderApi {

    private final PayRefundOrderManager payRefundOrderManager;

    /**
     * 获取指定商户指定的退款订单数量
     *
     * @param mchId       商户id
     * @param mchRefundNo 商户退款订单号
     * @param refundState 退款状态   如果为空则不携带参数查询
     */
    @Override
    public Long refundOrderCount(Long mchId, String mchRefundNo, Integer refundState) {
        return payRefundOrderManager.refundOrderCount(mchId, mchRefundNo, refundState);
    }

    /**
     * 获取指定类型全部退款金额
     *
     * @param payOrderId  支付订单ID
     * @param refundState 退款状态
     */
    @Override
    public Long sumRefundAmount(Long payOrderId, Integer refundState) {
        return payRefundOrderManager.sumRefundAmount(payOrderId, refundState);
    }

    /**
     * 创建退款订单
     *
     * @param refundOrder 退款订单请求体
     * @return 退款订单创建结果
     */
    @Override
    public CreateRefundOrderVO createRefundOrder(CreateRefundOrderDTO refundOrder) {
        return payRefundOrderManager.createRefundOrder(refundOrder);
    }

    /**
     * 更新退款订单内容
     *
     * @param updateRefundOrderState 待更新请求体
     */
    @Override
    public Boolean updateInitOrderStateThrowException(UpdateRefundOrderState updateRefundOrderState) {
        return payRefundOrderManager.updateInitOrderStateThrowException(updateRefundOrderState);
    }

    /**
     * 获取支付退款订单信息
     *
     * @param refundOrderId 退款订单id
     */
    @Override
    public PayRefundOrderVO refundOrderInfo(Long refundOrderId) {
        return payRefundOrderManager.refundOrderInfo(refundOrderId);
    }

    /**
     * 更新退款通知状态
     *
     * @param orderId 退款订单id
     * @param state   退款状态
     */
    @Override
    public Boolean updateNotifySent(Long orderId, Integer state) {
        return payRefundOrderManager.updateNotifySent(orderId, state);
    }


}
