package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.enums.PlatformConfigEnum;
import com.baosight.payment.system.dao.entity.SystemPlatformConfiguration;
import com.baosight.payment.system.dao.manager.SystemPlatformConfigurationManager;
import com.baosight.payment.system.dao.mapper.SystemPlatformConfigurationMapper;
import com.baosight.payment.system.pojo.dto.req.SystemConfigReqDTO;
import com.baosight.payment.system.pojo.dto.resp.SystemConfigRespDTO;
import com.baosight.payment.system.service.SystemPlatformConfigurationService;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.utils.enums.IBaseEnum;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.json.JsonUtil;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.baosight.payment.system.error.PlatformConfigError.CONFIG_TYPE_ERROR;
import static com.baosight.payment.system.error.PlatformConfigError.SYSTEM_PARAM_VALUE_FORMAT_ERROR;

/**
 * @author longjiangran
 * @description 针对表【system_platform_configuration(系统平台配置)】的数据库操作Service实现
 * @createDate 2026-03-10 15:43:32
 */
@Service
@RequiredArgsConstructor
public class SystemPlatformConfigurationServiceImpl extends ServiceImpl<SystemPlatformConfigurationMapper, SystemPlatformConfiguration>
        implements SystemPlatformConfigurationService {

    private final SystemPlatformConfigurationManager systemPlatformConfigurationManager;


    /**
     * 保存系统配置信息
     *
     * @param type            配置类型
     * @param systemConfigReq 配置信息请求参数
     */
    @Override
    public Boolean saveSystemConfig(String type, SystemConfigReqDTO systemConfigReq) {

        PlatformConfigEnum configEnum = IBaseEnum.getByCode(PlatformConfigEnum.class, type);

        Assert.isNull(configEnum, ApiException.supplier(CONFIG_TYPE_ERROR));
        SystemPlatformConfiguration configuration = systemPlatformConfigurationManager.lambdaQuery()
                .eq(SystemPlatformConfiguration::getConfigType, type).one();

        Class<?> aClass = SystemPlatformConfiguration.getConfiguration(type);
        Assert.isNull(aClass, ApiException.supplier(CONFIG_TYPE_ERROR));

        if (ObjectUtils.isEmpty(configuration)) {
            configuration = new SystemPlatformConfiguration();
        }
        configuration.setConfigType(type);
        try {
            String json = JsonUtil.toJson(systemConfigReq.getValue());
            JsonUtil.parse(json, aClass);
            configuration.setConfigValue(json);
        } catch (Exception e) {
            throw new ApiException(SYSTEM_PARAM_VALUE_FORMAT_ERROR);
        }
        configuration.setUpdateBy(UserContext.INSTANCE.userId());
        return systemPlatformConfigurationManager.saveOrUpdate(configuration);
    }

    /**
     * 获取系统配置信息
     *
     * @param type 配置信息类型
     */
    @Override
    public SystemConfigRespDTO getSystemConfig(String type) {
        PlatformConfigEnum configEnum = IBaseEnum.getByCode(PlatformConfigEnum.class, type);
        Assert.isNull(configEnum, ApiException.supplier(CONFIG_TYPE_ERROR));

        SystemPlatformConfiguration configuration = systemPlatformConfigurationManager.lambdaQuery().eq(SystemPlatformConfiguration::getConfigType, type).one();
        Class<?> aClass = SystemPlatformConfiguration.getConfiguration(type);
        Object value = JsonUtil.parse(configuration.getConfigValue(), aClass);
        SystemConfigRespDTO result = new SystemConfigRespDTO();
        result.setValue(value);
        return result;
    }
}




