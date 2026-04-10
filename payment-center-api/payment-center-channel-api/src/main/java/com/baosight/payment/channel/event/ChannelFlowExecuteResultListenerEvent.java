package com.baosight.payment.channel.event;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/8
 */
@Data
public class ChannelFlowExecuteResultListenerEvent {

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道id
     */
    private  Long channelId;

    /**
     * 商户id
     */
    private  Long clientId;

    /**
     * 商户类型
     */
    private Integer clientType;
    /**
     * 渠道流程类型
     */
    private String channelFlowType;

    /**
     * 执行结果
     */
    private String executeResult;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 异常信息
     */
    private String exceptionMsg;
    /**
     * 处理时间
     */
    private Date handleTime;
}
