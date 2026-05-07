package com.baosight.payment.channel.service.impl;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.channel.error.ChannelError;
import com.baosight.payment.channel.handler.notify.IChannelNotifyOption;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 接受回调通知后，改变mall商城订单状态
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Service
@RequiredArgsConstructor
public class PayNotifyProcessor {

    private final List<IChannelNotifyOption> notifyOptions;

    /**
     * 处理回调结果，改变mall订单状态
     *
     * @param dto 统一渠道回调结果
     */
    public Boolean process(UnifiedPayNotifyDTO dto) {
        IChannelNotifyOption option = notifyOptions.stream()
                .filter(item -> item.support(dto))
                .findFirst()
                .orElseThrow(() -> new ServiceException(ChannelError.CALLBACK_HANDLER_NOT_FOUND.getMsg()));
        return option.process(dto);
    }

}
