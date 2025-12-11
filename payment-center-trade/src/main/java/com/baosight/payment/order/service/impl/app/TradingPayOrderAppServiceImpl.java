package com.baosight.payment.order.service.impl.app;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.annotation.ApplicationService;
import com.baosight.payment.order.manager.PayOrderManager;
import com.baosight.payment.order.pojo.dto.PayOrderPageDTO;
import com.baosight.payment.order.pojo.vo.PayOrderInfoVO;
import com.baosight.payment.order.pojo.vo.PayOrderPageVO;
import com.baosight.payment.order.service.app.TradingPayOrderAppService;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@ApplicationService
@RequiredArgsConstructor
public class TradingPayOrderAppServiceImpl implements TradingPayOrderAppService {

    private final PayOrderManager payOrderManager;

    /**
     * 支付订单列表
     *
     * @param payOrderPage 支付订单列表
     */
    @Override
    public PageResponse<PayOrderPageVO> payOrderPage(PayOrderPageDTO payOrderPage) {
        return payOrderManager.payOrderPage(payOrderPage);
    }

    /**
     * 获取支付订单信息
     *
     * @param id orderId
     */
    @Override
    public PayOrderInfoVO info(Long id) {
        return payOrderManager.info(id);
    }
}
