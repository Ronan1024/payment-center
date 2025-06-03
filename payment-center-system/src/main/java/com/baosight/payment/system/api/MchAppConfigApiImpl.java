package com.baosight.payment.system.api;

import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.dao.TongLianConfigVO;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.system.enums.TongLianInfoType;
import com.baosight.payment.system.manager.PayInterfaceConfigManager;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayMchAppService;
import com.baosight.payment.system.tonglian.MembershipAndAccountHandler;
import com.baosight.payment.system.tonglian.TongLianClient;
import com.baosight.payment.vo.MchAppConfigInfoVO;
import com.baosight.payment.vo.MchAppInfoVO;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.saas.entity.DynamicForm;
import com.baosight.utils.json.JsonUtil;
import com.baosight.web.exception.ApiException;
import com.baosight.web.properties.ProjectInfo;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MchAppConfigApiImpl implements MchAppConfigApi {
    private final PayInterfaceConfigService payInterfaceConfigService;
    private final PayMchAppService payMchAppService;
    private final PayInterfaceConfigManager payInterfaceConfigManager;
    private final MchInfoApi mchInfoApi;
    private final ProjectInfo projectInfo;

    /**
     * 获取应用信息
     *
     * @param mchNo
     * @param appNo
     */
    @Override
    public MchAppInfoVO mchApiInfo(Long mchNo, String appNo) {
        return payMchAppService.appInfo(mchNo, appNo);
    }

    /**
     * 获取
     *
     * @param mchNo
     * @param AppId
     */
    @Override
    public MchAppConfigInfoVO McAppConfigInfo(Long mchNo, Long AppId) {
        return null;
    }

    /**
     * 获取通联配置信息
     *
     * @param mchNo
     */
    @Override
    public TongLianConfigVO tongLianConfig(Long mchNo) {
        PayInterfaceConfig interfaceConfig = payInterfaceConfigService.getIsvInterfaceConfig(mchNo);
        List<DynamicForm> dynamicForms = JsonUtil.parseArray(interfaceConfig.getInterfaceParams(), DynamicForm.class);
        Map<String, Object> collect = dynamicForms.stream().collect(Collectors.toMap(DynamicForm::getName, DynamicForm::getValue));
        return JsonUtil.parse(JsonUtil.toJson(collect), TongLianConfigVO.class);
    }

    /**
     * 获取通联支付配置
     *
     * @param mchId         商户id
     * @param interfaceCode 接口编号
     * @param isvId         服务商id
     */
    @Override
    public TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig(Long mchId, String interfaceCode, Long isvId) {
        return payInterfaceConfigManager.tongLianIsvAndMchConfig(mchId, interfaceCode, isvId);
    }

    /**
     * 获取指定接口code 下的所有商户配置
     *
     * @param interfaceCode 接口code
     */
    @Override
    public List<MchInterfaceConfigVO> isvConfig(String interfaceCode) {
        return payInterfaceConfigManager.mchConfig(interfaceCode);
    }

    /**
     * 获取下级子商户的配置信息
     *
     * @param isvId         服务商id
     * @param interfaceCode 支付接口编号
     */
    @Override
    public List<MchInterfaceConfigVO> mchConfig(Long isvId, String interfaceCode) {
        return payInterfaceConfigManager.mchConfig(isvId, interfaceCode);
    }

    @Override
    public JsonNode getMchBankCardNo(Long mchId) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);

        long reqTraceNum = SnowflakeIdUtil.nextId();
        String signNum = String.valueOf(mchId);
        TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfigDAO = this.tongLianIsvAndMchConfig(mchId, PayInterfaceCode.TONG_LIAN_PAY.getCode(), mchInfoVO.getIsvId());
        // 获取用户信息
        String url;
        if (!projectInfo.hasDev()) {
            url = "https://ibsapi.allinpay.com/yst-service-api/tm/handle";
        } else {
            url = "https://ibsapi.allinpay.com/yst-service-api/tm/handle";
        }
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberQuery(reqTraceNum, signNum, TongLianInfoType.BANK_ACCOUNT_INFO);
        TongLianClient tongLianClient = new TongLianClient(tongLianIsvAndMchConfigDAO.isvConfig());
        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, url);
        var ref = new Object() {
            JsonNode bankCardNo;
        };

        if (Boolean.TRUE.equals(response.getSuccess())) {

            response.getResult().get("acctInfo").forEach(item -> {
                if (item.get("isSettleAcct").asText().equals("1") && item.get("bindStatus").asText().equals("1")) {
                    ref.bankCardNo = item;
                }
            });
        } else {
            throw new ApiException("10000", "获取银行卡号失败");
        }

        return ref.bankCardNo;
    }


}
