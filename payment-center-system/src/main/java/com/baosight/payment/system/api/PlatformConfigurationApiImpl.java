package com.baosight.payment.system.api;

import com.baosight.payment.api.PlatformConfigurationApi;
import com.baosight.payment.dao.resp.AllInRespDTO;
import com.baosight.payment.enums.PlatformConfigEnum;
import com.baosight.payment.system.convert.SystemPlatformConfigurationConvert;
import com.baosight.payment.system.dao.entity.SystemPlatformConfiguration;
import com.baosight.payment.system.dao.manager.SystemPlatformConfigurationManager;
import com.ronan.common.json.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
@Component
@RequiredArgsConstructor
public class PlatformConfigurationApiImpl implements PlatformConfigurationApi {
    private final SystemPlatformConfigurationManager systemPlatformConfigurationManager;


    /**
     * 获取通联支付所有支付配置信息
     */
    @Override
    public AllInRespDTO allInPayConfig() {
        SystemPlatformConfiguration systemPlatformConfiguration = systemPlatformConfigurationManager.lambdaQuery()
                .eq(SystemPlatformConfiguration::getConfigType, PlatformConfigEnum.ALL_IN.getCode()).one();
        if (ObjectUtils.isEmpty(systemPlatformConfiguration)) {
            return null;
        }
        Class<?> configuration = SystemPlatformConfiguration.getConfiguration(PlatformConfigEnum.ALL_IN.getCode());
        SystemPlatformConfiguration.AllIn parse = (SystemPlatformConfiguration.AllIn) JsonUtil.parse(systemPlatformConfiguration.getConfigValue(), configuration);
        return SystemPlatformConfigurationConvert.INSTANCE.toAllInRespDTO(parse);
    }
}
