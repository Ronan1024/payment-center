package com.baosight.payment.system.service;

import com.baosight.payment.system.dao.entity.SystemPlatformConfiguration;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.system.pojo.dto.req.SystemConfigReqDTO;
import com.baosight.payment.system.pojo.dto.resp.SystemConfigRespDTO;

/**
* @author longjiangran
* @description 针对表【system_platform_configuration(系统平台配置)】的数据库操作Service
* @createDate 2026-03-10 15:43:32
*/
public interface SystemPlatformConfigurationService extends IService<SystemPlatformConfiguration> {

    /**
     * 保存系统配置信息
     * @param type 配置类型
     * @param systemConfigReq 配置信息请求参数
     */
    Boolean saveSystemConfig(String type, SystemConfigReqDTO systemConfigReq);

    /**
     * 获取系统配置信息
     * @param type 配置信息类型
     */
    SystemConfigRespDTO getSystemConfig(String type);
}
