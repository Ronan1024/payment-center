package com.baosight.payment.channel.api;

import com.baosight.payment.channel.dto.resp.ChannelInfoRespDTO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
public interface  ChannelInfoApi {


    /**
     * 获取渠道信息
     * @param code 渠道编号
     */
    ChannelInfoRespDTO info(String code);


}
