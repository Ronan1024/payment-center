package com.baosight.payment.channel.service;

import com.baosight.payment.channel.pojo.resp.ChannelCodeRespDTO;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
public interface PayChannelService {

    /**
     * 获取所有的支付渠道信息
     */
    List<ChannelCodeRespDTO> list();

}
