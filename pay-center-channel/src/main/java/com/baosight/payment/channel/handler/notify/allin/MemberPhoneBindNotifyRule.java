package com.baosight.payment.channel.handler.notify.allin;

import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.handler.notify.ChannelNotifyRequest;
import com.baosight.payment.channel.handler.notify.IChannelNotifyRule;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 会员绑定手机号结果回调处理。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Slf4j
@Component
public class MemberPhoneBindNotifyRule implements IChannelNotifyRule {


    /**
     * 渠道编号
     */
    @Override
    public ChannelCode channelCode() {
        return ChannelCode.ALLIN_PAY;
    }

    /**
     * 获取支持的回调事件类型。
     *
     * @return 会员绑定手机号事件类型
     */
    @Override
    public ChannelEventType eventType() {
        return ChannelEventType.MEMBER_PHONE_BIND;
    }

//    /**
//     * 处理会员绑定手机号结果回调。
//     *
//     * @param dto 统一渠道回调结果
//     * @return true 表示处理成功
//     */
//    @Override
//    public Boolean process(UnifiedPayNotifyDTO dto) {
//        log.info("会员绑定手机号回调处理完成 channelCode={}, bizOrderNo={}, status={}",
//                dto.getChannelCode(), dto.getBizOrderNo(), dto.getHandleStatus());
//        return true;
//    }

    /**
     * 判断当前规则是否支持该入站请求。
     *
     * @param request 渠道回调入站请求
     * @param key
     * @return true 表示支持
     */
    @Override
    public boolean support(ChannelNotifyRequest request, String key) {
        return key.equals(channelCode().code() + "-" + eventType().code());
    }

    /**
     * 解析渠道回调为统一结果。
     *
     * @param request 渠道回调入站请求
     * @return 统一渠道回调结果
     */
    @Override
    public UnifiedPayNotifyDTO parse(ChannelNotifyRequest request) {
        UnifiedPayNotifyDTO result = new UnifiedPayNotifyDTO();


        return result;
    }

    /**
     * 渠道成功响应内容。
     *
     * @return 渠道成功响应内容
     */
    @Override
    public String successResponse() {
        return "SUCCESS";
    }

    /**
     * 执行处理
     *
     * @param dto 统一渠道回调结果
     * @return true 表示处理成功
     */
    @Override
    public Boolean process(UnifiedPayNotifyDTO dto) {
        return null;
    }
}
