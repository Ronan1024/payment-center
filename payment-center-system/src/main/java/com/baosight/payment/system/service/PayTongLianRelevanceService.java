package com.baosight.payment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.system.pojo.dto.TongLianAgreementDTO;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
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
     * @param mchId          商户id
     * @param payInterfaceId 通联支付信息
     */
    Boolean bindSybMerchantCode(Long mchId, Long payInterfaceId);

    /**
     * 通联会员绑定收银宝商户
     *
     * @param mchId                   商户id
     * @param tongLianIsvAndMchConfig 通联服务商与商家配置
     * @param interfaceId
     */
    Boolean bindSybMerchantCode(Long mchId, TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig, Long interfaceId);

    /**
     * 通联会员绑定手机号申请
     *
     * @param mchId              商户id
     * @param phone              手机号
     * @param payInterfaceConfig 通联支付配置信息
     * @param hasLegalPerson     是否法人手机号
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


    /**
     * 通联会员绑定手机号申请
     *
     * @param mchId          商户id
     * @param mchConfig      通联支付配置信息
     * @param phone          手机号
     * @param hasLegalPerson 是否法人手机号
     */
    Boolean bindPhoneReport(Long mchId, TongLianIsvAndMchConfigDAO mchConfig, Long interfaceId, String phone, Boolean hasLegalPerson);

    /**
     * 通联支付线上签约
     *
     * @param mchId                   商户ID
     * @param tongLianIsvAndMchConfig 通联支付配置
     * @param interfaceRate           接口费率
     */
    String contractSign(Long mchId, TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig, Long interfaceRate);

    /**
     * 确认绑定手机号
     *
     * @param phone      手机号
     * @param mchId      商户id
     * @param verifyCode 校验code
     * @param mchConfig  商户配置
     * @param hasBind    是否为绑定
     */
    Boolean confirmBindPhone(String phone, Long mchId, String verifyCode, TongLianIsvAndMchConfigDAO mchConfig, Boolean hasBind);
}
