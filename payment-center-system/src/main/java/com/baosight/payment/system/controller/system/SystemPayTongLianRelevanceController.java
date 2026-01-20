package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayTongLianRelevanceService;
import com.baosight.payment.system.service.app.PayTongLianRelevanceAppService;
import com.ronan.common.validation.annotation.Mobile;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: payment-center
 * @description: 支付平台处理商户通联相关操作
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping( "/pm/tl/relevance/manage")
public class SystemPayTongLianRelevanceController {

    private final PayTongLianRelevanceAppService payTongLianRelevanceAppService;

    /**
     * 绑定收银宝
     */
    @PostMapping("/bind/syb/{mchId}/{interfaceId}")
    public Boolean bindSyb(@PathVariable("mchId") Long mchId, @PathVariable("interfaceId") Long interfaceId) {
        return payTongLianRelevanceAppService.bindSybMerchantCode(mchId, interfaceId);
    }


    /**
     * 绑定手机号申请
     *
     * @param mchId          商家id
     * @param interfaceId    接口id
     * @param phone          手机号
     * @param hasLegalPerson 是否为法人
     */
    @PostMapping("/bind/phone/report/{mchId}/{interfaceId}/{phone}/{hasLegalPerson}")
    public Boolean bindPhoneReport(@PathVariable("mchId") Long mchId, @PathVariable("interfaceId") Long interfaceId, @PathVariable("phone") @Validated @Mobile String phone, @PathVariable("hasLegalPerson") Boolean hasLegalPerson) {
        return payTongLianRelevanceAppService.bindPhoneReport(mchId, phone, hasLegalPerson, interfaceId);
    }

    /**
     * 确认绑定/解绑手机号
     *
     * @param phone      手机号
     * @param verifyCode 验证码
     */
    @PostMapping("/bind/phone/confirm/{mchId}/{phone}/{verifyCode}")
    public Boolean confirmBindPhone(@PathVariable("mchId") Long mchId, @PathVariable("phone") @Validated @Mobile String phone, @PathVariable("verifyCode") String verifyCode) {
        return payTongLianRelevanceAppService.confirmBindPhone(mchId, phone, verifyCode);
    }

    /**
     * 线上协议签约申请
     */
    @PostMapping("/contract/sign/{mchId}")
    public String contractSign(@PathVariable("mchId") Long mchId) {
        return payTongLianRelevanceAppService.contractSign(mchId);

    }
}
