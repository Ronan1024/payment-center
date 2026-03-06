package com.baosight.payment.channel.pojo.resp;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Data
public class ChannelCodeRespDTO {

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道编号
     */
    private String channelCode;
}
