package com.baosight.payment.controller;

import com.baosight.payment.pojo.dto.PayInterfaceConfigDTO;
import com.baosight.saas.constant.BaseUrlConstant;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 支付接口配置
 */
@RestController
@RequestMapping(BaseUrlConstant.SYSTEM + "/pm/pay/interface/conf")
public class PayInterfaceConfigController {

//    /**
//     * 获取支付配置信息
//     */
//    @GetMapping
//    public PayInterfaceConfigVO getPayInterfaceConfig() {
//    }

    /**
     * 保存更新支付配置
     */
    @PostMapping
    public Boolean savePayInterface(@RequestBody @Validated PayInterfaceConfigDTO interfaceConfigDTO) {
        return Boolean.FALSE;
    }
}
