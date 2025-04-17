package com.baosight.payment.pojo.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/30
 */
@Data
public class RefundOrderResponse {

    /**
     * 支付系统退款订单号
     **/
    private String refundOrderNo;

    /**
     * 商户发起的退款订单号
     **/
    private String mchRefundNo;

    /**
     * 订单支付金额
     **/
    private String payAmount;

    /**
     * 申请退款金额
     **/
    private String refundAmount;

    /**
     * 退款状态
     **/
    private String state;

    /**
     * 业务返回信息
     */
    private String respMsg;
}
