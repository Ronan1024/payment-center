package com.baosight.payment.order.api;

import com.baosight.payment.order.api.dto.CreateRefundOrderDTO;
import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.CreateRefundOrderVO;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;

/**
 * @program: payment-center
 * @description: 退款订单接口
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
public interface RefundOrderApi {

    /**
     * 获取指定商户指定的退款订单数量
     *
     * @param mchId       商户id
     * @param mchRefundNo 商户退款订单号
     * @param refundState 退款状态 如果为空则不携带参数查询
     */
    Long refundOrderCount(Long mchId, String mchRefundNo, Integer refundState);

    /**
     * 获取指定类型全部退款金额
     *
     * @param payOrderId  支付订单ID
     * @param refundState 退款状态
     */
    Long sumRefundAmount(Long payOrderId, Integer refundState);

    /**
     * 创建退款订单
     *
     * @param refundOrder 退款订单请求体
     * @return 退款订单创建结果
     */
    CreateRefundOrderVO createRefundOrder(CreateRefundOrderDTO refundOrder);

    /**
     * 更新退款订单内容
     *
     * @param updateRefundOrderState 待更新请求体
     */
    Boolean updateInitOrderStateThrowException(UpdateRefundOrderState updateRefundOrderState);

    /**
     * 获取支付退款订单信息
     *
     * @param refundOrderId 退款订单id
     */
    PayRefundOrderVO refundOrderInfo(Long refundOrderId);

    /**
     * 更新退款通知状态
     *
     * @param orderId 退款订单id
     * @param state   退款状态
     */
    Boolean updateNotifySent(Long orderId, Integer state);
}
