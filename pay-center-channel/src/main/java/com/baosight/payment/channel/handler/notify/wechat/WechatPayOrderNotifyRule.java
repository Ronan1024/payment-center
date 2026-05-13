package com.baosight.payment.channel.handler.notify.wechat;

import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRoute;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 微信支付订单结果回调识别规则。
 *
 * @author Codex
 * @date 2026/05/13
 */
@Component
@Order(10)
public class WechatPayOrderNotifyRule extends WechatNotifyRule {

    /**
     * 支持的回调事件类型。
     *
     * @return 支付订单事件类型
     */
    @Override
    public ChannelEventType eventType() {
        return ChannelEventType.PAY_ORDER;
    }

    /**
     * 判断是否为微信支付订单回调。
     *
     * @param request 渠道回调入站请求
     * @param route   渠道回调路由
     * @return true 表示微信支付订单回调
     */
    @Override
    public boolean support(ChannelNotifyRequest request, ChannelNotifyRoute route) {
        return route.matches(ChannelCode.WECHAT_PAY, ChannelEventType.PAY_ORDER)
                && isWechatNotify(request);
    }
}
