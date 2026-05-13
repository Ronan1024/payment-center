package com.baosight.payment.channel.handler.notify.wechat;

import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRoute;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 微信退款结果回调识别规则。
 *
 * @author Codex
 * @date 2026/05/13
 */
@Component
@Order(11)
public class WechatRefundNotifyRule extends WechatNotifyRule {

    /**
     * 支持的回调事件类型。
     *
     * @return 退款事件类型
     */
    @Override
    public ChannelEventType eventType() {
        return ChannelEventType.REFUND;
    }

    /**
     * 判断是否为微信退款回调。
     *
     * @param request 渠道回调入站请求
     * @param route   渠道回调路由
     * @return true 表示微信退款回调
     */
    @Override
    public boolean support(ChannelNotifyRequest request, ChannelNotifyRoute route) {
        return route.matches(ChannelCode.WECHAT_PAY, ChannelEventType.REFUND)
                && isWechatNotify(request);
    }
}
