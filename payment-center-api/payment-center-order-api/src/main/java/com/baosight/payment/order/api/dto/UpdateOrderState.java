package com.baosight.payment.order.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 更新订单状态
 * @author: L.J.Ran
 * @create: 2025/3/28
 */
@Data
public class UpdateOrderState {
    private Long orderId;


    private Integer orderState;

    /**
     * 渠道商户号
     */
    private String channelMchNo;

    /**
     * 渠道用户标识,如微信openId,支付宝账号
     */
    private String channelUser;

    /**
     * 渠道订单号
     */
    private String channelOrderNo;

    /**
     * 订单完成时间
     */
    private Date finishTime;

    /**
     * 渠道支付错误码
     */
    private String errCode;

    /**
     * 渠道支付错误描述
     */
    private String errMsg;

    /**
     * 渠道返回信息
     */
    private String channelResult;

    /**
     * 支付机构渠道支付orderId
     */
    private String payAgencyChannelOrder;

    /**
     * 交易模式
     */
    private String tradeMode;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 订单类型
     */
    private String type;

    /**
     * 子订单类型
     */
    private Integer subType;
}
