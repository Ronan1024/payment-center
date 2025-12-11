package com.baosight.payment.order.service.app;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.order.pojo.dto.PayOrderPageDTO;
import com.baosight.payment.order.pojo.vo.PayOrderInfoVO;
import com.baosight.payment.order.pojo.vo.PayOrderPageVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
public interface TradingPayOrderAppService {
    /**
     * 支付订单列表
     * @param payOrderPage 支付订单列表
     */
    PageResponse<PayOrderPageVO> payOrderPage(PayOrderPageDTO payOrderPage);

    /**
     * 获取支付订单信息
     */
    PayOrderInfoVO info(Long id);
}
