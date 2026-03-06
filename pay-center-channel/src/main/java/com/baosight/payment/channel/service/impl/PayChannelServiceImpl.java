package com.baosight.payment.channel.service.impl;

import com.baosight.payment.channel.handler.ChannelContext;
import com.baosight.payment.channel.pojo.resp.ChannelCodeRespDTO;
import com.baosight.payment.channel.service.PayChannelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Service
public class PayChannelServiceImpl implements PayChannelService {

    /**
     * 获取所有的支付渠道信息
     */
    @Override
    public List<ChannelCodeRespDTO> list() {
        return ChannelContext.channelList().entrySet()
                .stream().map(e -> {
                    ChannelCodeRespDTO result = new ChannelCodeRespDTO();
                    result.setChannelCode(e.getKey());
                    result.setChannelName(e.getValue());
                    return result;
                }).toList();
    }
}
