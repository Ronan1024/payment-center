package com.baosight.payment.system.controller.system;

import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.system.enums.TongLianInfoType;
import com.baosight.payment.system.tonglian.MembershipAndAccountHandler;
import com.baosight.payment.system.tonglian.TongLianClient;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.saas.auth.model.PlatformUserInfo;
import com.fasterxml.jackson.databind.JsonNode;
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
public class Test {

    private final MchAppConfigApi mchAppConfigApi;

    @GetMapping("/test")
    public Object test() {
        JsonNode mchBankCardNo = mchAppConfigApi.getMchBankCardNo(1905630620138393601L);

        long nextId = SnowflakeIdUtil.nextId();
        TongLianClient.SendBuild sendBuild = MembershipAndAccountHandler.memberQuery(nextId, "1894259806383198208", TongLianInfoType.BANK_ACCOUNT_INFO);
        TongLianIsvConfigDAO tongLianIsvConfigDAO = new TongLianIsvConfigDAO();
        tongLianIsvConfigDAO.setAppId("21897176485399416833");
        tongLianIsvConfigDAO.setPrivateKeyStr("MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgShpaUdD93dH9XBrhhVuAwzYo+GYunwZXdkK+rt0x8BigCgYIKoEcz1UBgi2hRANCAAQDymTM+r6CdzLiLYNw+eepEDBVxfsNTtpAi+hFSDRhz05Pn2Yz4rNY/BShniFWsJTxnvUcGYDJQbNzp7m0eHD3");
        tongLianIsvConfigDAO.setAllinPayPublicKeyStr("MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAE/VKHBem28IXD30yuZN1QcNgGE4gzqgd/eX1ZEouUleLNfrnQJkOs7LzAag3q10uaH/e9+5JyJDx3ULfKS4QZPw==");
        TongLianClient tongLianClient = new TongLianClient(tongLianIsvConfigDAO);

        TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, "https://ibsapi.allinpay.com/yst-service-api/tm/handle");
        JsonNode result = response.getResult();
        var ref = new Object() {
            String bankCardNo;
        };
        result.get("acctInfo").forEach(item -> {
            if (item.get("isSettleAcct").asText().equals("1") && item.get("bindStatus").asText().equals("1")) {
                ref.bankCardNo = item.get("bankCardNo").asText();
            }
        });
        System.out.println(ref.bankCardNo);
        return result;

    }

    @GetMapping("/test2")
    public String test1() {
        PlatformUserInfo platformUserInfo = UserContext.INSTANCE.userInfo();
        log.info("user info: {}", platformUserInfo.toString());
        return "ok";
    }
}
