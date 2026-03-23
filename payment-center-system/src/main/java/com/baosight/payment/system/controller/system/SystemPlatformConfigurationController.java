package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.dto.req.SystemConfigReqDTO;
import com.baosight.payment.system.pojo.dto.resp.SystemConfigRespDTO;
import com.baosight.payment.system.service.SystemPlatformConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 运营端平台基础配置
 *
 * @program: payment-center
 * @description: 运营端平台基础配置
 * @author: L.J.Ran
 * @create: 2026/3/10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/platform/config")
public class SystemPlatformConfigurationController {

    private final SystemPlatformConfigurationService systemPlatformConfigurationService;

    /**
     * 保存系统配置
     * @param type 系统配置类型
     * @param systemConfigReq  支付配置
     * @return
     */
    @PostMapping("/{type}")
    public Boolean saveSystemConfig(@PathVariable("type") String type,@RequestBody@Validated SystemConfigReqDTO systemConfigReq){
        return systemPlatformConfigurationService.saveSystemConfig(type, systemConfigReq);
    }

    /**
     * 获取配置信息
     * @param type 系统配置信息类型
     */
    @GetMapping("/{type}")
    public SystemConfigRespDTO getSystemConfig(@PathVariable("type") String type){
        return systemPlatformConfigurationService.getSystemConfig(type);
    }


}
