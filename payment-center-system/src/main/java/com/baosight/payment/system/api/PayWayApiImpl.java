package com.baosight.payment.system.api;

import com.baosight.payment.api.PayWayApi;
import com.baosight.payment.system.convert.PayWayConvert;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayMchAppService;
import com.baosight.payment.system.service.PayWayService;
import com.baosight.payment.vo.ApiPayWayVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayWayApiImpl implements PayWayApi {
    private final PayMchAppService payMchAppService;
    private final PayInterfaceConfigService payInterfaceConfigService;
    private final PayWayService payWayService;

    /**
     * 获取应用支付方式
     */
    @Override
    public List<ApiPayWayVO> payWayList(Integer id, Long mchId, Long appId) {
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigService.getIsvInterfaceConfig(mchId);
        List<Long> list = Arrays.stream(payInterfaceConfig.getPayWay().split(",")).map(Long::valueOf).toList();
        List<PayWay> payWay = payWayService.getPayWayList(list);
        return payWay.stream().filter(e -> e.getPayingClient().equals(id)).map(PayWayConvert.INSTANCE::toApiPayWayVO).toList();
    }
}
