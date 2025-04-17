package com.baosight.payment.order.api;

import com.baosight.payment.order.api.dto.CrCreateOrderDTO;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.order.api.dto.UpdateOrderState;
import com.baosight.payment.order.api.vo.CreateOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;

import java.util.List;

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
     *
     * @param orderId 订单id
     */
    OrderVO orderInfo(Long orderId);

    /**
     * 更新订单异步通知状态
     *
     * @param orderId      订单id
     * @param notifyStatus 异步通知状态
     */
    Boolean updateNotifySent(Long orderId, Integer notifyStatus);

    /**
     * 获取商户支付订单数据
     *
     * @param mchId      商户ID
     * @param outTradeNo 商家订单号
     */
    int getPayOrderCount(Long mchId, String outTradeNo);

    /**
     * 创建订单
     *
     * @param payOrder 支付订单
     */
    CreateOrderVO createOrder(CreateOrderDTO payOrder);

    /**
     * 更新订单状态
     *
     * @param updateOrderState 更新状态信息
     */
    Boolean updateInitOrderStateThrowException( UpdateOrderState updateOrderState);

    /**
     * 获取订单信息
     *
     * @param mchId      系统商户id
     * @param mchOrderNo 商户订单号
     * @param payOrderId 系统订单号
     */
    OrderVO orderInfo(Long mchId, String mchOrderNo, String payOrderId);

    /**
     * 获取订单列表
     *
     * @param orderId 订单id
     */
    List<OrderVO> orderList(List<Long> orderId);

    /**
     * 更新订单对账状态
     */
    Boolean updateOrderCheckState(List<Long> orderIdList);
}
