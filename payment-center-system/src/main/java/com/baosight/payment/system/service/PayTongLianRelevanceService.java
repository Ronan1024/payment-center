package com.baosight.payment.system.service;

import com.baosight.payment.system.pojo.dto.TongLianAgreementDTO;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.TongLianRelevanceVO;

/**
 * @author longjiangran
 * @description 针对表【pay_tong_lian_relevance(通联支付扩展关联信息)】的数据库操作Service
 * @createDate 2025-02-20 16:24:07
 */
public interface PayTongLianRelevanceService extends IService<PayTongLianRelevance> {

    /**
     * 初始化通联扩展信息
     *
     * @param mchId 商户id
     */
    Boolean init(Long mchId);


    /**
     * 通联会员绑定收银宝商户
     *
     * @param mchId              商户id
     * @param payInterfaceConfig 通联支付信息
     */
    Boolean bindSybMerchantCode(Long mchId, PayInterfaceConfigVO payInterfaceConfig);

    /**
     * 通联会员绑定手机号申请
     *
     * @param mchId              商户id
     * @param phone              手机号
     * @param payInterfaceConfig 通联支付配置信息
     * @param hasLegalPerson 是否法人手机号
     */
    Boolean bindPhone(Long mchId, String phone, PayInterfaceConfigVO payInterfaceConfig, Boolean hasLegalPerson);

    /**
     * 确认绑定/解绑手机号
     *
     * @param phone      绑定或解绑手机
     * @param mchId      商户id
     * @param verifyCode 短信验证码
     * @param hasBind    是否为绑定操作
     */
    Boolean confirmBindPhone(String phone, Long mchId, String verifyCode, PayInterfaceConfigVO payInterfaceConfig, Boolean hasBind);

    /**
     * 获取商户与通联绑定信息
     *
     * @param mchId 商户id
     */
    TongLianRelevanceVO getRelevanceInfo(Long mchId);

    /**
     * 线上协议签约申请
     */
    String contractSign(Long mchId, PayInterfaceConfigVO payInterfaceConfigVO, TongLianAgreementDTO tongLianAgreement);
}
