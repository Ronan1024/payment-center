package com.baosight.payment.system.service.app;

/**
 * 商户配置与通联支付关联 APP Service
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/26
 */
public interface PayTongLianRelevanceAppService {
    /**
     * 绑定通联收银宝code
     *
     * @param mchId       商户ID
     * @param interfaceId 接口ID
     */
    Boolean bindSybMerchantCode(Long mchId, Long interfaceId);

    /**
     * 绑定通联手机号
     *
     * @param mchId          商户id
     * @param phone          手机号
     * @param hasLegalPerson 是否为法人手机号
     * @param interfaceId    接口id
     */
    Boolean bindPhoneReport(Long mchId, String phone, Boolean hasLegalPerson, Long interfaceId);

    /**
     * 通联线上协议签订
     *
     * @param mchId 商家id
     */
    String contractSign(Long mchId);

    /**
     * 确认绑定手机号
     *
     * @param mchId      商户号id
     * @param phone      手机号
     * @param verifyCode 校验手机号
     */
    Boolean confirmBindPhone(Long mchId, String phone, String verifyCode);
}
