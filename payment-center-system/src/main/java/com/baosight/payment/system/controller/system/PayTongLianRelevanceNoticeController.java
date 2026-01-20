package com.baosight.payment.system.controller.system;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baosight.payment.system.constant.SystemConstant;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.payment.system.service.PayTongLianRelevanceService;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @program: payment-center
 * @description: 处理通联接口的响应消息
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tl/notice")
@Slf4j
public class PayTongLianRelevanceNoticeController {
    private final PayTongLianRelevanceService payTongLianRelevanceService;
    private final PayInterfaceConfigService payInterfaceConfigService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 绑定手机号结果通知
     * mchId 为pay_mch_info的记录主键
     */
    @GetMapping("/bind/phone/{mchId}")
    public ResponseEntity bindPhone(HttpServletRequest request, @PathVariable Long mchId) {
        log.info("进入[" + mchId + "]绑定手机号回调");
        try {
            String body = getBody(request);
            JsonNode jsonNode = JsonUtil.readTree(body);
            String phone = jsonNode.get("phone").asText();
            if ("1".equals(jsonNode.get("bindResult").toString())) {
                // 更新 payTongLianRelevance.hasBindPhone = true
                // 操作成功修改状态绑定收银宝信息
                payTongLianRelevanceService.update(new LambdaUpdateWrapper<PayTongLianRelevance>()
                        .eq(PayTongLianRelevance::getMchId, mchId)
                        .set(PayTongLianRelevance::getHasBindPhone, Boolean.TRUE)
                        .set(PayTongLianRelevance::getPhone, phone));
            } else {
                // 绑定失败,删除redis缓存
                log.error("绑定手机号失败原因：" + jsonNode.get("bindErrorMsg").toString());
                String key = SystemConstant.getTongLianPhoneResp(String.valueOf(mchId), phone, Boolean.TRUE);
                redisTemplate.opsForValue().getAndDelete(key);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return ResponseEntity.ok("success");
    }

    /**
     * 会员协议签约结果通知
     */
    @GetMapping("/sign/{mchId}")
    public ResponseEntity bindSyb(HttpServletRequest request, @PathVariable Long mchId) {
        log.info("进入[" + mchId + "]签约回调");

        try {
            String body = getBody(request);
            JsonNode jsonNode = JsonUtil.readTree(body);
            if ("1".equals(jsonNode.get("couponAgreeStatus").toString())) { // 平台抽佣协议签约状态 = 签约成功
                // 更新 payTongLianRelevance.getHasContractSign() = true
                payTongLianRelevanceService.update(new LambdaUpdateWrapper<PayTongLianRelevance>()
                        .eq(PayTongLianRelevance::getMchId, mchId)
                        .set(PayTongLianRelevance::getHasContractSign, Boolean.TRUE));
                // 更新 PayInterfaceDefine.enable = true
                payInterfaceConfigService.update(new LambdaUpdateWrapper<PayInterfaceConfig>().eq(PayInterfaceConfig::getClientId,mchId).set(PayInterfaceConfig::getEnable,Boolean.TRUE));
            } else {
                // 签约失败
                log.error("签约失败原因：" + jsonNode.get("signErrorMsg").toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        return ResponseEntity.ok("success");
    }

    private String getBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

}
