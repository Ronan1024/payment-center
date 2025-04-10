package com.baosight.payment.system.api;

import com.baosight.payment.api.PayWayApi;
import com.baosight.payment.system.convert.PayWayConvert;
import com.baosight.payment.system.pojo.entity.PayMchPassage;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayMchAppService;
import com.baosight.payment.system.service.PayMchPassageService;
import com.baosight.payment.system.service.PayWayService;
import com.baosight.payment.vo.ApiPayWayVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayWayApiImpl implements PayWayApi {
    private final PayMchAppService payMchAppService;
    private final PayInterfaceConfigService payInterfaceConfigService;
    private final PayWayService payWayService;
    private final PayMchPassageService payMchPassageService;

    /**
     * 获取应用支付方式
     */
    @Override
    public List<ApiPayWayVO> payWayList(Integer id, Long mchId, Long appId) {
        // TODO 待处理 异常信息
        List<PayMchPassage> payMchPassageList = payMchPassageService.getPayPassageByAppId(appId, mchId);
        if (CollectionUtils.isEmpty(payMchPassageList)){
            return new ArrayList<>();
        }
        List<Long> payWayId = payMchPassageList.stream().map(PayMchPassage::getPayWayId).toList();
        List<PayWay> payWay = payWayService.getPayWayList(payWayId, id);
        return payWay.stream().map(PayWayConvert.INSTANCE::toApiPayWayVO).toList();
    }
}
