package com.baosight.payment.system.manager;

import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.system.tonglian.TongLianClient;

/**
 * @program: payment-center
 * @description: 支付平台处理商户通联聚合逻辑处理
 * @author: L.J.Ran
 * @create: 2025/3/26
 */
public interface PayTongLianRelevanceManager {


    /**
     * 绑定收银宝账号
     *
     * @param mchConfig 商家配置
     */
    TongLianClient.Response memberBindSyb(Long mchId, TongLianIsvAndMchConfigDAO mchConfig);


    /**
     * 更新收银宝绑定状态
     *
     * @param mchId       商户id
     * @param success     成功状态
     * @param signNum     通联账号
     * @param interfaceId 接口id
     */
    Boolean updateTongLianSybRelevance(Long mchId, Boolean success, String signNum, Long interfaceId);


    /**
     * 绑定收银宝手机号
     *
     * @param mchConfig      商家配置
     * @param mchId          商家id
     * @param phone          手机号
     * @param hasLegalPerson 是否为法人
     */
    String bindPhoneReport(TongLianIsvAndMchConfigDAO mchConfig, Long mchId, String phone, Boolean hasLegalPerson);

    /**
     * 申请通联线上签约
     *
     * @param tongLianIsvAndMchConfig 通联配置信息
     * @param interfaceRate           接口费率
     * @param mchId                   商家id
     */
    String onlineProtocolSignApply(TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig, Long interfaceRate, Long mchId);

    /**
     * 确认绑定通联手机号
     *
     * @param value      验证码
     * @param phone      手机号
     * @param signNum
     * @param mchConfig  商户配置
     * @param hasBind    是否为绑定
     * @param verifyCode
     */
    Boolean confirmBindPhone(String value, String phone, String signNum, TongLianIsvAndMchConfigDAO mchConfig, Boolean hasBind, String verifyCode);
}
