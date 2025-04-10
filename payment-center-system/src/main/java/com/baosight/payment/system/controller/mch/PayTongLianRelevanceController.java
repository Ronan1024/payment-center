package com.baosight.payment.system.controller.mch;

import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.pojo.dto.TongLianAgreementDTO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.TongLianRelevanceVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayTongLianRelevanceService;
import com.baosight.saas.context.SystemUserContext;
import com.baosight.utils.annotation.Mobile;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

@RestController
@RequiredArgsConstructor
@RequestMapping(SYSTEM + "/mch/tl/relevance/manage")
public class PayTongLianRelevanceController {

    private final PayTongLianRelevanceService payTongLianRelevanceService;
    private final PayInterfaceConfigService payInterfaceConfigService;

    @GetMapping("/info")
    public TongLianRelevanceVO relevanceInfo() {
        Long mchId = SystemUserContext.getCompanyId();
        return payTongLianRelevanceService.getRelevanceInfo(mchId);
    }


    @PostMapping("/bind/syb")
    public Boolean bindSyb() {
        Long mchId = SystemUserContext.getCompanyId();
        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchId, PayingAgency.TONG_LIAN);
//        return payTongLianRelevanceService.bindSybMerchantCode(mchId, payInterfaceConfigVO);
        return null;
    }

    /**
     * 绑定手机号申请
     */
    @PostMapping("/bind/phone/{phone}/{hasLegalPerson}")
    public Boolean bindPhone(@PathVariable("phone") @Validated @Mobile String phone, @PathVariable("hasLegalPerson") Boolean hasLegalPerson) {
        Long mchId = SystemUserContext.getCompanyId();
        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchId, PayingAgency.TONG_LIAN);
        return payTongLianRelevanceService.bindPhone(mchId, phone, payInterfaceConfigVO, hasLegalPerson);
    }

    /**
     * 确认绑定/解绑手机号
     *
     * @param phone      手机号
     * @param verifyCode 验证码
     */
    @PostMapping("/bind/phone/confirm/{phone}/{verifyCode}")
    public Boolean confirmBindPhone(@PathVariable("phone") @Validated @Mobile String phone,
                                    @PathVariable("verifyCode") String verifyCode) {
        Long mchId = SystemUserContext.getCompanyId();
        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchId, PayingAgency.TONG_LIAN);
        return payTongLianRelevanceService.confirmBindPhone(phone, mchId, verifyCode, payInterfaceConfigVO, Boolean.TRUE);
    }

    /**
     * 线上协议签约申请
     */
    @PostMapping("/contract/sign")
    public String contractSign(@RequestBody @Validated TongLianAgreementDTO tongLianAgreement) {
        Long mchId = SystemUserContext.getCompanyId();
        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchId, PayingAgency.TONG_LIAN);
        return payTongLianRelevanceService.contractSign(mchId, payInterfaceConfigVO, tongLianAgreement);
    }
}
