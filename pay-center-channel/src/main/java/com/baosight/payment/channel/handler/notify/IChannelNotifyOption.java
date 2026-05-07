package com.baosight.payment.channel.handler.notify;

import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;

/**
 * 渠道回调业务处理器。
 *
 * @author L.J.Ran
 * @date 2026/04/20
 */
public interface IChannelNotifyOption {

    /**
     * 支持的回调事件类型。
     *
     * @return 回调事件类型
     */
    ChannelEventType eventType();

    /**
     * 是否支持当前回调。
     *
     * @param dto 统一渠道回调结果
     * @return true 表示支持
     */
    default boolean support(UnifiedPayNotifyDTO dto) {
        return dto != null && eventType().equals(dto.getEventType());
    }

    /**
     * 处理统一回调结果。
     *
     * @param dto 统一渠道回调结果
     * @return true 表示处理成功
     */
    Boolean process(UnifiedPayNotifyDTO dto);

}
