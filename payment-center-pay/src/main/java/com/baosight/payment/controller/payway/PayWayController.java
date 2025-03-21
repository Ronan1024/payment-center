package com.baosight.payment.controller.payway;

import com.baosight.payment.enums.PayingClient;
import com.baosight.payment.model.PayWayModel;
import com.baosight.payment.service.ApiPayWayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pay/way")
public class PayWayController {
    private final ApiPayWayService apiPayWayService;

    /**
     * 获取可用支付列表
     */
    @GetMapping("/{client}/{mchId}/{appId}")
    public List<PayWayModel> clientPayWayList(@PathVariable String client, @PathVariable Long appId, @PathVariable Long mchId) {
        PayingClient payingClient = PayingClient.payingClient(client);
        return apiPayWayService.paywayList(payingClient.getCode(), appId, mchId);
    }
}
