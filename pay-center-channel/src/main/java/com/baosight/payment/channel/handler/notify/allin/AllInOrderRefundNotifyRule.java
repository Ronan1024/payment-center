package com.baosight.payment.channel.handler.notify.allin;

import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRoute;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 通联订单退款结果回调识别规则。
 *
 * @author Codex
 * @date 2026/05/13
 */
@Component
@Order(31)
public class AllInOrderRefundNotifyRule extends AllInNotifyRule {

    @Override
    public ChannelEventType eventType() {
        return ChannelEventType.REFUND;
    }

    @Override
    public boolean support(ChannelNotifyRequest request, ChannelNotifyRoute route) {
        return route.matches(ChannelCode.ALLIN_PAY, ChannelEventType.REFUND)
                && isAllInOrderNotify(request);
    }
}
