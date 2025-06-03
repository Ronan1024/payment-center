package com.baosight.payment.order.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/30
 */
@Data
public class UpdateRefundOrderState {
    private Long refundId;


    private Integer refundState;


    /**
     * 渠道订单号
     */
    private String channelOrderNo;

    /**
     * 渠道返回信息
     */
    private String chanelResult;

    /**
     * 渠道支付错误码
     */
    private String errCode;

    /**
     * 渠道支付错误描述
     */
    private String errMsg;

    /**
     * 订单完成时间
     */
    private Date finishTime;

    private Date notifyTime;

}
