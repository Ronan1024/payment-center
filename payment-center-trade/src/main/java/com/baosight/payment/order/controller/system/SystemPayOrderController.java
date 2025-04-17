package com.baosight.payment.order.controller.system;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.order.pojo.dto.PayOrderPageDTO;
import com.baosight.payment.order.pojo.vo.PayOrderInfoVO;
import com.baosight.payment.order.pojo.vo.PayOrderPageVO;
import com.baosight.payment.order.service.app.TradingPayOrderAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.baosight.saas.constant.LoginType.SYSTEM;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/2
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(SYSTEM + "/pay/order/manage")
public class SystemPayOrderController {

    private final TradingPayOrderAppService payOrderAppService;

    /**
     * 订单列表
     *
     * @param payOrderPage 订单列表查询请求体
     */
    @PostMapping("/page")
    public PageResponse<PayOrderPageVO> payOrderPage(@RequestBody @Validated PayOrderPageDTO payOrderPage) {
        return payOrderAppService.payOrderPage(payOrderPage);
    }

    /**
     * 支付订单信息
     *
     */
    @GetMapping("/{id}")
    public PayOrderInfoVO info(@PathVariable("id") Long id) {
        return payOrderAppService.info(id);
    }
}
