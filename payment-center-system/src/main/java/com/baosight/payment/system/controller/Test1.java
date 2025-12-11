package com.baosight.payment.system.controller;

import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.saas.auth.model.PlatformUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/22
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class Test1 {

    private final MchAppConfigApi mchAppConfigApi;

    @GetMapping("/test1")
    public String test1() {
        PlatformUserInfo platformUserInfo = UserContext.INSTANCE.userInfo();
        log.info("user info: {}", platformUserInfo.toString());
        return "ok";
    }
}
