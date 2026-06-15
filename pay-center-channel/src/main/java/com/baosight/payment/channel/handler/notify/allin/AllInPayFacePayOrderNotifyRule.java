//package com.baosight.payment.channel.handler.notify.allin;
//
//import com.baosight.payment.channel.enums.ChannelCode;
//import com.baosight.payment.channel.enums.ChannelEventType;
//import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
//import com.baosight.payment.channel.handler.notify.ChannelNotifyRoute;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
///**
// * 通联收银宝支付订单结果回调识别规则。
// *
// * @author Codex
// * @date 2026/05/13
// */
//@Component
//@Order(20)
//public class AllInPayFacePayOrderNotifyRule extends AllInPayFaceNotifyRule {
//
//    @Override
//    public ChannelEventType eventType() {
//        return ChannelEventType.PAY_ORDER;
//    }
//
//    @Override
//    public boolean support(ChannelNotifyRequest request, ChannelNotifyRoute route) {
//        return route.matches(ChannelCode.ALLIN_PAY, ChannelEventType.PAY_ORDER)
//                && isAllInPayFaceNotify(request);
//    }
//}
