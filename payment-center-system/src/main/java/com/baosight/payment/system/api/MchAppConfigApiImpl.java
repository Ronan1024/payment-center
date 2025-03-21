package com.baosight.payment.system.api;

import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.dao.TongLianConfigVO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.vo.MchAppConfigInfoVO;
import com.baosight.payment.vo.MchAppInfoVO;
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

    /**
     * 获取应用信息
     *
     * @param mchNo
     * @param AppId
     */
    @Override
    public MchAppInfoVO mchApiInfo(Long mchNo, Long AppId) {
        return null;
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

}
