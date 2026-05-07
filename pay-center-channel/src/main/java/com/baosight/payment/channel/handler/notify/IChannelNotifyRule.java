package com.baosight.payment.channel.handler.notify;

import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.enums.ChannelCode;
import com.baosight.payment.channel.handler.BaseChannel;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;

/**
 * 渠道回调识别与解析规则。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
public interface IChannelNotifyRule extends BaseChannel {


    /**
     * 渠道编号
     */
    ChannelCode channelCode();


    /**
     * 支持的回调事件类型。
     *
     * @return 回调事件类型
     */
    ChannelEventType eventType();

    /**
     * 判断当前规则是否支持该入站请求。
     *
     * @param request 渠道回调入站请求
     * @param tag     标识
     * @return true 表示支持
     */
    boolean support(ChannelNotifyRequest request, String tag);

    /**
     * 解析渠道回调为统一结果。
     *
     * @param request 渠道回调入站请求
     * @return 统一渠道回调结果
     */
    UnifiedPayNotifyDTO parse(ChannelNotifyRequest request);

    /**
     * 渠道成功响应内容。
     *
     * @return 渠道成功响应内容
     */
    String successResponse();

    /**
     * 执行处理
     *
     * @param dto 统一渠道回调结果
     * @return true 表示处理成功
     */
    Boolean process(UnifiedPayNotifyDTO dto);
}
