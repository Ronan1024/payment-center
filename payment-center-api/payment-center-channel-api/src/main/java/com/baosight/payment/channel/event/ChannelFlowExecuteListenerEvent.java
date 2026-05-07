package com.baosight.payment.channel.event;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/8
 */
@Data
@Accessors(chain = true)
public class ChannelFlowExecuteListenerEvent {
    /**
     *  渠道编号
     */
    private String channelCode;
    /**
     * 渠道ID
     */
    private Long channelId;


    /**
     * 渠道执行器
     */
    private String channelFlowType;

    /**
     * 客户端ID
     */
    private Long clientId;

    /**
     * 客户端类型
     */
    private Integer clientType;



    /**
     * 参数体
     */
    private String body;
}
