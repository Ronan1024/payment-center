package com.baosight.payment.system.manager.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.enums.MchType;
import com.baosight.payment.system.enums.TongLianInfoType;
import com.baosight.payment.system.error.TongLianError;
import com.baosight.payment.system.manager.PayTongLianRelevanceManager;
import com.baosight.payment.system.mapper.PayInterfaceConfigMapper;
import com.baosight.payment.system.mapper.PayTongLianRelevanceMapper;
import com.baosight.payment.system.mapper.RequestInterfaceRecordMapper;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.system.pojo.dao.tonglian.TongLianMemberBasicInfoDAO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import com.baosight.payment.system.pojo.entity.RequestInterfaceRecord;
import com.baosight.payment.system.tonglian.MembershipAndAccountHandler;
import com.baosight.payment.system.tonglian.TongLianClient;
import com.baosight.web.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/26
 */

@Slf4j
@Manager
@RequiredArgsConstructor
public class PayTongLianRelevanceManagerImpl implements PayTongLianRelevanceManager {

    private final PayTongLianRelevanceMapper payTongLianRelevanceMapper;
    private final PayInterfaceConfigMapper payInterfaceConfigMapper;
    private final RequestInterfaceRecordMapper requestInterfaceRecordMapper;


    @Value("${pay.user_url}")
    private String tlUserUrl;

    /**
     * 绑定收银宝账号
     *
     * @param mchConfig 商家配置
     */
    @Override
    public TongLianClient.Response memberBindSyb(Long mchId, TongLianIsvAndMchConfigDAO mchConfig) {
        // 获取获取服务商配置
        TongLianClient tongLianClient = new TongLianClient(mchConfig.isvConfig());
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberBindSyb(SnowflakeIdUtil.nextId(), String.valueOf(mchId), mchConfig.mchConfig().getSignNum());
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, tlUserUrl);
        RequestInterfaceRecord requestInterfaceRecord = RequestInterfaceRecord.tongLianInit(sendBuild.getParams(), response);
        requestInterfaceRecordMapper.insert(requestInterfaceRecord);
        return response;
    }

    /**
     * 更新收银宝绑定状态
     *
     * @param mchId       商户id
     * @param success     成功状态
     * @param signNum     通联账号
     * @param interfaceId 接口id
     */
    @Override
    public Boolean updateTongLianSybRelevance(Long mchId, Boolean success, String signNum, Long interfaceId) {
        payTongLianRelevanceMapper.update(new LambdaUpdateWrapper<PayTongLianRelevance>()
                .eq(PayTongLianRelevance::getMchId, mchId)
                .isNull(PayTongLianRelevance::getSybMerchantCode)
                .set(PayTongLianRelevance::getSybMerchantCode, signNum));
        if (Boolean.TRUE.equals(success)) {
            payInterfaceConfigMapper.update(new LambdaUpdateWrapper<PayInterfaceConfig>()
                    .eq(PayInterfaceConfig::getInterfaceId, interfaceId)
                    .eq(PayInterfaceConfig::getClientId, mchId)
                    .eq(PayInterfaceConfig::getClientType, MchType.SUB_MERCHANT.getCode())
                    .set(PayInterfaceConfig::getMchChannelUser, signNum)
            );
        }

        return Boolean.TRUE;
    }

    @Override
    public String bindPhoneReport(TongLianIsvAndMchConfigDAO mchConfig, Long mchId, String phone, Boolean hasLegalPerson) {
        long reqTraceNum = SnowflakeIdUtil.nextId();
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberBindMobile(reqTraceNum, String.valueOf(mchId), phone, hasLegalPerson);
        TongLianClient tongLianClient = new TongLianClient(mchConfig.isvConfig());
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, tlUserUrl);
        RequestInterfaceRecord requestInterfaceRecord = RequestInterfaceRecord.tongLianInit(sendBuild.getParams(), response);
        requestInterfaceRecordMapper.insert(requestInterfaceRecord);
        if (Boolean.FALSE.equals(response.getSuccess())) {
            throw new ApiException(response.getRespCode(), response.getErrorMsg());
        }
        return response.get("respTraceNum").asText();
    }

    /**
     * 申请通联线上签约
     *
     * @param tongLianIsvAndMchConfig 通联配置信息
     * @param interfaceRate           接口费率
     * @param mchId                   商家id
     */
    @Override
    public String onlineProtocolSignApply(TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig, Long interfaceRate, Long mchId) {
        long reqTraceNum = SnowflakeIdUtil.nextId();
        String signNum = String.valueOf(mchId);
        // 获取用户信息
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberQuery(reqTraceNum, signNum, TongLianInfoType.BASIC_INFO);
        TongLianClient tongLianClient = new TongLianClient(tongLianIsvAndMchConfig.isvConfig());
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, tlUserUrl);
        RequestInterfaceRecord requestInterfaceRecord = RequestInterfaceRecord.tongLianInit(sendBuild.getParams(), response);
        requestInterfaceRecordMapper.insert(requestInterfaceRecord);
        if (Boolean.FALSE.equals(response.success())) {
            throw new ApiException(TongLianError.TONG_LIAN_USER_INFO_ERROR);
        }
        TongLianMemberBasicInfoDAO memberBasicInfoDAO = TongLianInfoType.build(response.getResult(), TongLianInfoType.BASIC_INFO);
        TongLianClient.SendBuild protocolSendBuild = MembershipAndAccountHandler.onlineProtocolSignApply(SnowflakeIdUtil.nextId(), signNum, memberBasicInfoDAO.getEnterpriseName(), interfaceRate);
        TongLianClient.Response protocolResponse = tongLianClient.sendRequest(protocolSendBuild, tlUserUrl);
        RequestInterfaceRecord signApplyRecord = RequestInterfaceRecord.tongLianInit(protocolSendBuild.getParams(), protocolResponse);
        requestInterfaceRecordMapper.insert(signApplyRecord);
        if (Boolean.FALSE.equals(response.success())) {
            throw new ApiException(response.getRespCode(), response.getErrorMsg());
        }
        // 获取用户信息
        // TODO 需要进行签约缓存
        return protocolResponse.get("signAgreementUrl").asText();
    }

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
    @Override
    public Boolean confirmBindPhone(String value, String phone, String signNum, TongLianIsvAndMchConfigDAO mchConfig, Boolean hasBind, String verifyCode) {
        long reqTraceNum = SnowflakeIdUtil.nextId();
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.confirmBindPhone(reqTraceNum, signNum, phone, value, verifyCode);
        TongLianClient tongLianClient = new TongLianClient(mchConfig.isvConfig());
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, tlUserUrl);
        RequestInterfaceRecord requestInterfaceRecord = RequestInterfaceRecord.tongLianInit(sendBuild.getParams(), response);
        requestInterfaceRecordMapper.insert(requestInterfaceRecord);
        if (Boolean.FALSE.equals(response.success())) {
            throw new ApiException(TongLianError.TONG_LIAN_USER_INFO_ERROR);
        }
        return Boolean.TRUE;
    }
}
