package com.baosight.payment.system.api;

import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.dao.TongLianConfigVO;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.system.manager.PayInterfaceConfigManager;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayMchAppService;
import com.baosight.payment.vo.MchAppConfigInfoVO;
import com.baosight.payment.vo.MchAppInfoVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.saas.entity.DynamicForm;
import com.baosight.utils.json.JsonUtil;
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


}
