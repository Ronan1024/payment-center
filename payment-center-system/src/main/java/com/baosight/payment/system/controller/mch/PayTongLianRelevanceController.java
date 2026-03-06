package com.baosight.payment.system.controller.mch;

import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.pojo.dto.TongLianAgreementDTO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.TongLianRelevanceVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayTongLianRelevanceService;
import com.ronan.common.validation.annotation.Mobile;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
//@RequestMapping(SYSTEM + "/mch/tl/relevance/manage")
@RequestMapping("/mch/tl/relevance/manage")
public class PayTongLianRelevanceController {

    private final PayTongLianRelevanceService payTongLianRelevanceService;
    private final PayInterfaceConfigService payInterfaceConfigService;

    @GetMapping("/info")
    public TongLianRelevanceVO relevanceInfo() {
//        Long mchId = SystemUserContext.getCompanyId();
        // TODO 商户id
        Long mchId = 0L;
        return payTongLianRelevanceService.getRelevanceInfo(mchId);
    }


    /**
     * 会员绑定收银宝商户
     * @param mchId
     * @return
     */
    @PostMapping("/bind/syb")
    public Boolean bindSyb(Long mchId) {
        // 获取商户签约的支付接口ID
        Long interfaceId = payInterfaceConfigService.getMchInterfaceIdByPayingAgency(mchId, PayingAgency.ALL_IN);
        if(interfaceId == null){
            throw new IllegalStateException("商户还未签约通联支付");
        }
        return payTongLianRelevanceService.bindSybMerchantCode(mchId, interfaceId);
    }

    /**
     * 绑定手机号申请
     *
     * @param phone 手机号
     * @param hasLegalPerson 是否为法人手机号
     */
    @PostMapping("/bind/phone/{phone}/{hasLegalPerson}")
    public Boolean bindPhone(@PathVariable("phone") @Validated @Mobile String phone, @PathVariable("hasLegalPerson") Boolean hasLegalPerson) {
        Long mchId = 0L;
        // TODO 商户id
        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchId, PayingAgency.ALL_IN);
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
//        Long mchId = SystemUserContext.getCompanyId();
        // TODO 商户id
        Long mchId = 0L;
        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchId, PayingAgency.ALL_IN);
        return payTongLianRelevanceService.confirmBindPhone(phone, mchId, verifyCode, payInterfaceConfigVO, Boolean.TRUE);
    }

    /**
     * 线上协议签约申请
     */
    @PostMapping("/contract/sign")
    public String contractSign(@RequestBody @Validated TongLianAgreementDTO tongLianAgreement) {
//        Long mchId = SystemUserContext.getCompanyId();
        // TODO 商户id
        Long mchId = 0L;
        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchId, PayingAgency.ALL_IN);
        return payTongLianRelevanceService.contractSign(mchId, payInterfaceConfigVO, tongLianAgreement);
    }

    /**
     * 会员协议签约结果通知
     */
//    public String querySign()
}
