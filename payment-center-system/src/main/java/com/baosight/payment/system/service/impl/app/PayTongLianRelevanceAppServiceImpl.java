package com.baosight.payment.system.service.impl.app;

import com.baosight.payment.annotation.ApplicationService;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.error.PayInterfaceConfigError;
import com.baosight.payment.system.error.PayInterfaceError;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.TongLianRelevanceVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.payment.system.service.PayTongLianRelevanceService;
import com.baosight.payment.system.service.app.PayTongLianRelevanceAppService;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.utils.utils.Assert;
import com.baosight.web.core.exception.ApiException;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/26
 */
@ApplicationService
@RequiredArgsConstructor
public class PayTongLianRelevanceAppServiceImpl implements PayTongLianRelevanceAppService {

    private final PayTongLianRelevanceService payTongLianRelevanceService;
    private final PayInterfaceDefineService payInterfaceDefineService;
    private final PayInterfaceConfigService payInterfaceConfigService;
    private final MchInfoApi mchInfoApi;

    /**
     * 绑定通联收银宝code
     *
     * @param mchId       商户ID
     * @param interfaceId 接口ID
     */
    @Override
    public Boolean bindSybMerchantCode(Long mchId, Long interfaceId) {
        TongLianRelevanceVO relevanceInfo = payTongLianRelevanceService.getRelevanceInfo(mchId);
        if (Boolean.TRUE.equals(relevanceInfo.getHasBindSyb())) {
            return Boolean.TRUE;
        }
        TongLianIsvAndMchConfigDAO mchConfig = payInterfaceConfigService.getTongLianIsvAndMchConfig(mchId, interfaceId);
        return payTongLianRelevanceService.bindSybMerchantCode(mchId, mchConfig, interfaceId);
    }

    /**
     * 绑定通联手机号
     *
     * @param mchId          商户id
     * @param phone          手机号
     * @param hasLegalPerson 是否为法人手机号
     * @param interfaceId
     */
    @Override
    public Boolean bindPhoneReport(Long mchId, String phone, Boolean hasLegalPerson, Long interfaceId) {
        TongLianRelevanceVO relevanceInfo = payTongLianRelevanceService.getRelevanceInfo(mchId);
        if (Boolean.TRUE.equals(relevanceInfo.getHasBindPhone())) {
            return Boolean.TRUE;
        }
        TongLianIsvAndMchConfigDAO mchConfig = payInterfaceConfigService.getTongLianIsvAndMchConfig(mchId, interfaceId);
        return payTongLianRelevanceService.bindPhoneReport(mchId, mchConfig, interfaceId, phone, hasLegalPerson);
    }

    /**
     * 通联线上协议签订
     *
     * @param mchId 商家id
     */
    @Override
    public String contractSign(Long mchId) {
        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineService.payInterfaceDefineByCode(PayInterfaceCode.TONG_LIAN_PAY.code());
        Assert.isNull(payInterfaceDefine, ApiException.supplier(PayInterfaceError.PAY_INTERFACE_CHANNEL_NOT_CONFIG));
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);

        PayInterfaceConfigVO payInterfaceConfigVO = payInterfaceConfigService.getConfigInfo(mchInfoVO.getIsvId(), PayingAgency.TONG_LIAN, payInterfaceDefine.getId());
        Assert.isNull(payInterfaceConfigVO, ApiException.supplier(PayInterfaceConfigError.MERCHANT_NOT_CONFIG_PAY_INTERFACE));
        Assert.isNull(payInterfaceConfigVO.getInterfaceRate(), ApiException.supplier(PayInterfaceConfigError.PAY_RATE_NOT_CONFIG));

        TongLianIsvAndMchConfigDAO mchConfig = payInterfaceConfigService.getTongLianIsvAndMchConfig(mchId, payInterfaceDefine.getId());

        return payTongLianRelevanceService.contractSign(mchId, mchConfig, payInterfaceConfigVO.getInterfaceRate());
    }

    /**
     * 确认绑定手机号
     *
     * @param mchId      商户号id
     * @param phone      手机号
     * @param verifyCode 校验手机号验证码
     */
    @Override
    public Boolean confirmBindPhone(Long mchId, String phone, String verifyCode) {
        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineService.payInterfaceDefineByCode(PayInterfaceCode.TONG_LIAN_PAY.code());
        Assert.isNull(payInterfaceDefine, ApiException.supplier(PayInterfaceError.PAY_INTERFACE_CHANNEL_NOT_CONFIG));
        TongLianIsvAndMchConfigDAO mchConfig = payInterfaceConfigService.getTongLianIsvAndMchConfig(mchId, payInterfaceDefine.getId());
        return payTongLianRelevanceService.confirmBindPhone(phone, mchId, verifyCode, mchConfig, Boolean.TRUE);
    }
}
