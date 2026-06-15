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
// * 通联协议签订结果回调识别规则。
// *
// * @author Codex
// * @date 2026/05/13
// */
//@Component
//@Order(32)
//public class AllInAgreementSignNotifyRule extends AllInNotifyRule {
//
//    @Override
//    public ChannelEventType eventType() {
//        return ChannelEventType.AGREEMENT_SIGN;
//    }
//
//    @Override
//    public boolean support(ChannelNotifyRequest request, ChannelNotifyRoute route) {
//        return route.matches(ChannelCode.ALLIN_PAY, ChannelEventType.AGREEMENT_SIGN)
//                && isAllInOrderNotify(request);
//    }
//}
