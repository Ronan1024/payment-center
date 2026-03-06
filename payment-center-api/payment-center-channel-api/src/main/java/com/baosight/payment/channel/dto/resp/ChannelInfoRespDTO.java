package com.baosight.payment.channel.dto.resp;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Data
public class ChannelInfoRespDTO {

    /**
     * 渠道信息id
     */
    private Long id;


    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 是否启用
     */
    private Boolean enable;
}
