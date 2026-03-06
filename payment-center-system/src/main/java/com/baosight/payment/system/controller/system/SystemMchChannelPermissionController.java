package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.service.SystemMchChannelPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 运营端商户支付通道权限
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/paying/channel/permission")
public class SystemMchChannelPermissionController {
    private final SystemMchChannelPermissionService systemMchChannelPermissionService;


    /**
     * 保存商户渠道权限
     */
    @PostMapping()
    public Boolean saveMhChannelPermission(@RequestBody @Validated MchChannelPermissionReqDTO mchChannelPermission) {
        return systemMchChannelPermissionService.saveMhChannelPermission(mchChannelPermission);
    }
    


}
