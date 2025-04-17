package com.baosight.payment.check.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/15
 */
@Data
public class RegisterTradingFlowDTO {

    /**
     * 交易金额
     */
    private Long amount;

    /**
     * 渠道成本
     */
    private Long channelCost;

    /**
     * 交易类型
     */
    private String tradingType;

    /**
     * 交易模式
     */
    private Integer tradingMode;

    /**
     * 交易订单id
     */
    private Long orderId;

    /**
     * 渠道订单号
     */
    private String channelOrderId;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 交易时间(完成时间)
     */
    private Date tradingTime;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 原始订单id(退款时才会(存在值)
     */
    private Long originOrderId;

    /**
     * 渠道商户编号
     */
    private String channelMchNo;

    /**
     * 交易状态
     */
    private Integer tradingState;
}
