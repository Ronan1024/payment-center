package com.baosight.payment.dao.req;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
@Data
public class MchChannelFlowExecuteResultReqDTO {

    /**
     * 渠道编号
     */
    private String channelCode;

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

}
