//package com.baosight.payment.channel.handler.notify.allin;
//
//import com.baosight.payment.channel.enums.ChannelEventType;
//import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
//import com.baosight.payment.channel.handler.notify.ChannelNotifyRoute;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
///**
// * 通联会员绑定手机号结果回调处理。
// *
// * @author L.J.Ran
// * @date 2026/04/21
// */
//@Component
//@Order(33)
//public class AllInMemberPhoneBindNotifyRule extends AllInNotifyRule {
//
//    /**
//     * 获取支持的回调事件类型。
//     *
//     * @return 会员绑定手机号事件类型
//     */
//    @Override
//    public ChannelEventType eventType() {
//        return ChannelEventType.MEMBER_PHONE_BIND;
//    }
//
//    /**
//     * 判断当前规则是否支持该入站请求。
//     *
//     * @param request 渠道回调入站请求
//     * @param route   渠道回调路由
//     * @return true 表示支持
//     */
//    @Override
//    public boolean support(ChannelNotifyRequest request, ChannelNotifyRoute route) {
//        return route.matches(ChannelCode.ALLIN_PAY, ChannelEventType.MEMBER_PHONE_BIND)
//                && isAllInOrderNotify(request);
//    }
//}
