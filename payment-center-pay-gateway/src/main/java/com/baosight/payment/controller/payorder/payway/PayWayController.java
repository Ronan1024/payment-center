package com.baosight.payment.controller.payorder.payway;

import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayingClient;
import com.baosight.payment.model.payway.PayWayModel;
import com.baosight.payment.service.ApiPayWayService;
import com.baosight.payment.vo.MchAppInfoVO;
import com.baosight.payment.vo.MchInfoVO;
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
    private final MchInfoApi mchInfoApi;
    private final MchAppConfigApi mchAppConfigApi;

    /**
     * 获取可用支付列表
     */
    @GetMapping("/{client}/{mchNo}/{appNo}")
    public List<PayWayModel> clientPayWayList(@PathVariable String client, @PathVariable String appNo, @PathVariable String mchNo) {
        PayingClient payingClient = PayingClient.payingClient(client);
        MchInfoVO mchInfoVO = mchInfoApi.mchInfoBuMchNO(mchNo);
        MchAppInfoVO mchAppInfoVO = mchAppConfigApi.mchApiInfo(mchInfoVO.getId(), appNo);
        return apiPayWayService.paywayList(payingClient.getCode(), mchAppInfoVO.getId(), mchInfoVO.getId());
    }
}
