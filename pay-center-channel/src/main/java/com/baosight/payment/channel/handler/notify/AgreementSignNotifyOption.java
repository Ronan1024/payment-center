package com.baosight.payment.channel.handler.notify;

import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 协议签订结果回调处理。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Slf4j
@Component
public class AgreementSignNotifyOption implements IChannelNotifyOption {

    /**
     * 获取支持的回调事件类型。
     *
     * @return 协议签订事件类型
     */
    @Override
    public ChannelEventType eventType() {
        return ChannelEventType.AGREEMENT_SIGN;
    }

    /**
     * 处理协议签订结果回调。
     *
     * @param dto 统一渠道回调结果
     * @return true 表示处理成功
     */
    @Override
    public Boolean process(UnifiedPayNotifyDTO dto) {
        log.info("协议签订回调处理完成 channelCode={}, bizOrderNo={}, status={}",
                dto.getChannelCode(), "", dto.getHandleStatus());
        return true;
    }
}
