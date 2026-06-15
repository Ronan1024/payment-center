package com.baosight.payment.channel.handler;

import cn.hutool.core.date.DateUtil;
import com.baosight.payment.channel.enums.CallbackHandleStatus;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.payment.channel.pojo.dao.WechatNotifyDTO;
import com.baosight.payment.channel.service.PayNotifyHandler;
import com.baosight.payment.channel.service.impl.PayNotifyProcessor;
import com.baosight.payment.enums.ChannelCode;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@Order(10)
public class WechatNotifyHandler implements PayNotifyHandler {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private PayNotifyProcessor payNotifyProcessor;

    /**
     * 判断是否为微信回调
     * @param body
     * @param request
     * @return
     */
    @Override
    public boolean support(String body, HttpServletRequest request) {
        // 微信支付 v3 特有头部
        return request.getHeader("Wechatpay-Serial") != null;
    }


    /**
     * 处理微信回调，改变商城订单状态
     * @param body
     * @param request
     * @return
     */
    @Override
    public String handle(String body, HttpServletRequest request) {
        // 1. 验签（使用微信SDK 或 自己实现）
//        wechatVerifier.verify(request, body);

        // 2. 解析 JSON
        WechatNotifyDTO notify = JsonUtil.parse(body, WechatNotifyDTO.class);

        // 3. 解密 resource
//        String plain = wechatDecrypt(notify.getResource());

        // 4. 解析 decrypted JSON
//        WechatTransactionDTO txn = JSON.parseObject(plain, WechatTransactionDTO.class);
//
        UnifiedPayNotifyDTO result = new UnifiedPayNotifyDTO()
                .setChannelCode(ChannelCode.WECHAT_PAY)
                .setEventType(resolveEventType(notify))
                .setHandleStatus(resolveStatus(notify))
//                .setBizOrderNo(resolveText(body, "out_trade_no", "out_refund_no", "id"))
                .setChannelOrderNo(resolveText(body, "transaction_id", "refund_id", "id"))
                .setOriginChannelOrderNo(resolveText(body, "original_transaction_id"))
                .setFinishTime(resolveFinishTime(body, notify))
                .setRawBody(body);
        payNotifyProcessor.process(result);

        return "SUCCESS";  // 微信要求返回 SUCCESS
    }

    private ChannelEventType resolveEventType(WechatNotifyDTO notify) {
        String originalType = notify.getResource() == null ? null : notify.getResource().getOriginalType();
        if ("refund".equalsIgnoreCase(originalType) || containsIgnoreCase(notify.getEventType(), "REFUND")) {
            return ChannelEventType.REFUND;
        }
        return ChannelEventType.PAY_ORDER;
    }

    private CallbackHandleStatus resolveStatus(WechatNotifyDTO notify) {
        String eventType = notify.getEventType();
        if (containsIgnoreCase(eventType, "SUCCESS")) {
            return CallbackHandleStatus.SUCCESS;
        }
        if (containsIgnoreCase(eventType, "FAIL")
                || containsIgnoreCase(eventType, "CLOSED")
                || containsIgnoreCase(eventType, "ABNORMAL")) {
            return CallbackHandleStatus.FAIL;
        }
        return CallbackHandleStatus.PROCESSING;
    }

    private Date resolveFinishTime(String body, WechatNotifyDTO notify) {
        String value = resolveText(body, "success_time", "create_time");
        if (!StringUtils.hasText(value)) {
            value = notify.getCreateTime();
        }
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return DateUtil.parse(value);
    }

    private String resolveText(String body, String... names) {
        try {
            JsonNode root = OBJECT_MAPPER.readTree(body);
            for (String name : names) {
                JsonNode node = root.findValue(name);
                if (node != null && !node.isNull()) {
                    return node.asText();
                }
            }
        } catch (Exception ignored) {
            return null;
        }
        return null;
    }

    private boolean containsIgnoreCase(String value, String keyword) {
        return value != null && value.toUpperCase().contains(keyword.toUpperCase());
    }
}
