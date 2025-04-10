package com.baosight.payment.notify.pojo.dao;

import lombok.Data;

/**
 * @program: payment-center
 * @description: 支付通知
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Data
public class PayOrderNotifyDAO {
    /**
     * 支付流水号
     */
    private String orderId;

    /**
     * 支付源订单信息(退款时才会有有体现)
     */
    private String originOrderNo;


    /**
     * 订单类型
     */
    private Integer orderType;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 支付时间
     */
    private String payTime;

    /**
     * 完成时间
     */
    private String finishTime;
    /**
     * 商户id
     */
    private String mchNo;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 支付金额
     */
    private String payAmount;

    /**
     * 订单金额
     */
    private String orderAmount;

    /**
     * 服务商号
     */
    private String isvNo;

    /**
     * 支付类型
     */
    private Integer payType;

    /**
     * 支付订单返回状态
     */
    private String resultCode;

    /**
     * 失败信息
     */
    private String errMsg;


    /**
     * 商户订单id
     */
    private String mchOrderNo;


    /**
     * 商户原始订单(当退款时才会体现)
     */
    private String originMchOrderNo;


    private String channelOrderNo;

    /**
     * 支付机构渠道支付orderId
     */
    private String payAgencyChannelOrder;


    // TODO 处理渠道返回值
}
