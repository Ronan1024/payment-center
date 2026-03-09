package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.service.SystemMchChannelPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运营端商户支付通道权限
 *
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
    @Deprecated
    public Boolean saveMhChannelPermission(@RequestBody @Validated MchChannelPermissionReqDTO mchChannelPermission) {
        return systemMchChannelPermissionService.saveMhChannelPermission(mchChannelPermission);
    }


    /**
     * 获取商户已有的支付渠道权限
     *
     * @param type  当前商户类型
     * @param mchId 当前商户id
     */
    @GetMapping
    @Deprecated
    public List<String> mchChannelPermission(@RequestParam("type") Integer type, @RequestParam("mchId") Long mchId) {
        return systemMchChannelPermissionService.mchChannelPermission(type, mchId);
    }

}
