package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayTongLianRelevanceService;
import com.baosight.payment.system.service.app.PayTongLianRelevanceAppService;
import com.ronan.common.validation.annotation.Mobile;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * @program: payment-center
 * @description: 处理通联接口的响应消息
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping( "/tl/relevance/notice")
public class PayTongLianRelevanceController {


    /**
     * 处理绑定手机号申请的响应消息
     */
    @GetMapping("/bind/phone")
    public Boolean bindSyb(@PathVariable("mchId") Long mchId, @PathVariable("interfaceId") Long interfaceId) {
        return Boolean.TRUE;
    }


}
