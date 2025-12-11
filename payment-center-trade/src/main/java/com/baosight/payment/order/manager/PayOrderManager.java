package com.baosight.payment.order.manager;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.order.pojo.dto.PayOrderPageDTO;
import com.baosight.payment.order.pojo.vo.PayOrderInfoVO;
import com.baosight.payment.order.pojo.vo.PayOrderPageVO;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */

public interface PayOrderManager {
    /**
     * 获取订单信息
     *
     * @param mchId      系统商户id
     * @param mchOrderNo 商户订单号
     * @param payOrderNo 系统订单号
     */
    OrderVO orderInfo(Long mchId, String mchOrderNo, String payOrderNo);

    /**
     * 获取订单列表
     *
     * @param orderId 订单id
     */
    List<OrderVO> orderList(List<Long> orderId);

    /**
     * 更新订单对账状态
     *
     * @param orderIdList 订单id列表
     */
    Boolean updateOrderCheckState(List<Long> orderIdList);

    /**
     * 支付订单分页列表
     *
     * @param payOrderPage 支付订单page
     */
    PageResponse<PayOrderPageVO> payOrderPage(PayOrderPageDTO payOrderPage);

    /**
     * 支付订单详情
     * @param id id
     */
    PayOrderInfoVO info(Long id);
}
