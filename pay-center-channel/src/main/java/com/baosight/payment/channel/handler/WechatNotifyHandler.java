package com.baosight.payment.channel.handler;

import com.baosight.payment.channel.service.ChannelGatewayLogManager;
import com.baosight.payment.channel.service.impl.PayNotifyProcessor;
import com.baosight.utils.json.JsonUtil;
import com.baosight.payment.channel.pojo.dao.WechatNotifyDTO;
import com.baosight.payment.channel.service.PayNotifyHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WechatNotifyHandler implements PayNotifyHandler {

    @Autowired
    private PayNotifyProcessor payNotifyProcessor;

    @Autowired
    private ChannelGatewayLogManager channelGatewayLogManager;


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
//        UnifiedPayNotifyDTO result = new UnifiedPayNotifyDTO();
//        result.setChannel(PayChannel.WECHAT);
//        result.setStatus(txn.getTrade_state());
//        result.setOrderNo(txn.getOut_trade_no());
//        result.setThirdOrderNo(txn.getTransaction_id());
//        result.setFinishTime(txn.getSuccess_time());
//        result.setRawBody(body);

        // call common processor
//        payNotifyProcessor.process(result);

        return "SUCCESS";  // 微信要求返回 SUCCESS
    }
}
