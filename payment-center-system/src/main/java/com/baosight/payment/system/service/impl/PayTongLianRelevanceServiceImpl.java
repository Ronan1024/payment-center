package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.constant.SystemConstant;
import com.baosight.payment.system.enums.TongLianInfoType;
import com.baosight.payment.system.error.PayingAgencyError;
import com.baosight.payment.system.error.TongLianError;
import com.baosight.payment.system.mapper.PayTongLianRelevanceMapper;
import com.baosight.payment.system.pojo.dao.tonglian.TongLianIsvConfigDAO;
import com.baosight.payment.system.pojo.dao.tonglian.TongLianMemberBasicInfoDAO;
import com.baosight.payment.system.pojo.dto.TongLianAgreementDTO;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.TongLianRelevanceVO;
import com.baosight.payment.system.service.PayTongLianRelevanceService;
import com.baosight.payment.system.tonglian.MembershipAndAccountHandler;
import com.baosight.payment.system.tonglian.TongLianClient;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.saas.entity.DynamicForm;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import com.baosight.web.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author longjiangran
 * @description 针对表【pay_tong_lian_relevance(通联支付扩展关联信息)】的数据库操作Service实现
 * @createDate 2025-02-20 16:24:07
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayTongLianRelevanceServiceImpl extends ServiceImpl<PayTongLianRelevanceMapper, PayTongLianRelevance> implements PayTongLianRelevanceService {
    private final PayTongLianRelevanceMapper payTongLianRelevanceMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private MchInfoApi mchInfoApi;

    /**
     * 初始化通联扩展信息
     *
     * @param mchId 商户id
     */
    @Override
    public Boolean init(Long mchId) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        if (ObjectUtils.isEmpty(mchInfoVO)) {
            return Boolean.TRUE;
        }
        PayTongLianRelevance payTongLianRelevance = new PayTongLianRelevance();
        payTongLianRelevance.setMchId(mchId);
        return payTongLianRelevanceMapper.insert(payTongLianRelevance) > 0;
    }

    /**
     * 通联会员绑定收银宝商户
     *
     * @param mchId              商户id
     * @param payInterfaceConfig 通联支付信息
     */
    @Override
    public Boolean bindSybMerchantCode(Long mchId, PayInterfaceConfigVO payInterfaceConfig) {
        TongLianRelevanceVO relevanceInfo = getRelevanceInfo(mchId);
        if (Boolean.TRUE.equals(relevanceInfo.getHasBindSyb())) {
            return Boolean.TRUE;
        }
        TongLianIsvConfigDAO config = handler(payInterfaceConfig);
        TongLianClient tongLianClient = new TongLianClient(config);
        // TODO 通联商户号异常
//        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberBindSyb(SnowflakeIdUtil.nextId(), String.valueOf(mchId), config.getSignNum());
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberBindSyb(SnowflakeIdUtil.nextId(), String.valueOf(mchId), "");

        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, TongLianClient.URL);
        if (Boolean.TRUE.equals(response.getSuccess())) {
            payTongLianRelevanceMapper.update(new LambdaUpdateWrapper<PayTongLianRelevance>()
                    .eq(PayTongLianRelevance::getMchId, mchId)
                    .set(PayTongLianRelevance::getHasBindSyb, Boolean.TRUE)
            );
        }
        return response.getSuccess();
    }

    /**
     * 通联会员绑定手机号申请
     *
     * @param mchId              商户id
     * @param phone              手机号
     * @param payInterfaceConfig 通联支付配置信息
     * @param hasLegalPerson
     */
    @Override
    public Boolean bindPhone(Long mchId, String phone, PayInterfaceConfigVO payInterfaceConfig, Boolean hasLegalPerson) {
        String signNum = String.valueOf(mchId);
        String key = SystemConstant.getTongLianPhoneResp(signNum, phone, Boolean.TRUE);
        Assert.isTrue(Boolean.TRUE.equals(redisTemplate.hasKey(key)), ApiException.supplier(TongLianError.TONG_LIAN_MOBILE_BIND_ERROR));
        TongLianIsvConfigDAO config = handler(payInterfaceConfig);
        long reqTraceNum = SnowflakeIdUtil.nextId();
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberBindMobile(reqTraceNum, signNum, phone, hasLegalPerson);
        TongLianClient tongLianClient = new TongLianClient(config);
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, TongLianClient.URL);
        if (Boolean.TRUE.equals(response.success())) {
            String respTraceNum = response.get("respTraceNum").asText();
            redisTemplate.opsForValue().set(key, respTraceNum, 180, TimeUnit.SECONDS);
        }
        return response.getSuccess();
    }

    /**
     * 确认绑定/解绑手机号
     *
     * @param phone              绑定或解绑手机
     * @param mchId              商户id
     * @param verifyCode         短信验证码
     * @param payInterfaceConfig 支付接口配置
     * @param hasBind            是否为绑定操作
     */
    @Override
    public Boolean confirmBindPhone(String phone, Long mchId, String verifyCode, PayInterfaceConfigVO payInterfaceConfig, Boolean hasBind) {
        String signNum = String.valueOf(mchId);
        String key = SystemConstant.getTongLianPhoneResp(signNum, phone, hasBind);
        Assert.isFalse(Boolean.TRUE.equals(redisTemplate.hasKey(key)), ApiException.supplier(TongLianError.TONG_LIAN_MOBILE_UNBIND_ERROR));
        String value = String.valueOf(redisTemplate.opsForValue().get(key));
        long reqTraceNum = SnowflakeIdUtil.nextId();
        TongLianIsvConfigDAO config = handler(payInterfaceConfig);
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.confirmBindPhone(reqTraceNum, signNum, phone, value, verifyCode);
        TongLianClient tongLianClient = new TongLianClient(config);
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, TongLianClient.URL);
        if (response.success() && hasBind) {
            payTongLianRelevanceMapper.update(new LambdaUpdateWrapper<PayTongLianRelevance>()
                    .eq(PayTongLianRelevance::getMchId, mchId)
                    .set(PayTongLianRelevance::getHasBindPhone, Boolean.TRUE)
                    .set(PayTongLianRelevance::getPhone, phone)
            );
        }
        return response.success();
    }

    /**
     * 获取商户与通联绑定信息
     *
     * @param mchId 商户id
     */
    @Override
    public TongLianRelevanceVO getRelevanceInfo(Long mchId) {
        PayTongLianRelevance payTongLianRelevance = payTongLianRelevanceMapper.selectOne(new LambdaQueryWrapper<PayTongLianRelevance>()
                .eq(PayTongLianRelevance::getMchId, mchId)
        );
        TongLianRelevanceVO tongLianRelevanceVO = new TongLianRelevanceVO();
        if (!ObjectUtils.isEmpty(tongLianRelevanceVO)) {
            tongLianRelevanceVO.setHasBindPhone(payTongLianRelevance.getHasBindPhone());
            tongLianRelevanceVO.setHasBindSyb(payTongLianRelevance.getHasBindSyb());
            tongLianRelevanceVO.setHasContractSign(payTongLianRelevance.getHasContractSign());
        } else {
            tongLianRelevanceVO.setHasBindPhone(false);
            tongLianRelevanceVO.setHasBindSyb(false);
            tongLianRelevanceVO.setHasContractSign(false);
        }
        return tongLianRelevanceVO;
    }

    /**
     * 线上协议签约申请
     */
    @Override
    public String contractSign(Long mchId, PayInterfaceConfigVO payInterfaceConfigVO, TongLianAgreementDTO tongLianAgreement) {
        PayTongLianRelevance payTongLianRelevance = payTongLianRelevanceMapper.selectOne(new LambdaQueryWrapper<PayTongLianRelevance>()
                .eq(PayTongLianRelevance::getMchId, mchId)
        );
        if (payTongLianRelevance.getHasContractSign().equals(Boolean.TRUE)) {
            return "";
        }
        long reqTraceNum = SnowflakeIdUtil.nextId();
        String signNum = String.valueOf(mchId);

        // 获取用户信息
        TongLianIsvConfigDAO config = handler(payInterfaceConfigVO);
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberQuery(reqTraceNum, signNum, TongLianInfoType.BASIC_INFO);
        TongLianClient tongLianClient = new TongLianClient(config);
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, TongLianClient.URL);
        if (Boolean.FALSE.equals(response.success())) {
            throw new ApiException(TongLianError.TONG_LIAN_USER_INFO_ERROR);
        }
        TongLianMemberBasicInfoDAO memberBasicInfoDAO = TongLianInfoType.build(response.getResult(), TongLianInfoType.BASIC_INFO);
        System.out.println(memberBasicInfoDAO);
        TongLianClient.SendBuild protocolSendBuild = MembershipAndAccountHandler.onlineProtocolSignApply(SnowflakeIdUtil.nextId(), signNum, memberBasicInfoDAO.getEnterpriseName(), tongLianAgreement);
        TongLianClient.Response protocolResponse = tongLianClient.sendRequest(protocolSendBuild, TongLianClient.URL);
        log.info("返回信息： {}", protocolResponse.getResult());
        if (Boolean.FALSE.equals(response.success())) {
            log.error("{}", protocolResponse.getResult());
        }
        //        System.out.println(response.getResult());
//        String name = response.get("name").asText();
        // 获取用户信息
        // TODO 需要进行签约缓存
        return protocolResponse.get("signAgreementUrl").asText();
    }

    private TongLianIsvConfigDAO handler(PayInterfaceConfigVO payInterfaceConfig) {
        Assert.isFalse(!ObjectUtils.isEmpty(payInterfaceConfig) && payInterfaceConfig.getPayingAgency().equals(PayingAgency.TONG_LIAN.getCode()), ApiException.supplier(PayingAgencyError.PAYING_AGENCY_ERROR, PayingAgency.TONG_LIAN.getMsg()));
        List<DynamicForm> interfaceParam = payInterfaceConfig.getInterfaceParam();
        Map<String, Object> collect = interfaceParam.stream().collect(Collectors.toMap(DynamicForm::getName, DynamicForm::getValue));
        return JsonUtil.parse(JsonUtil.toJson(collect), TongLianIsvConfigDAO.class);
    }
}




