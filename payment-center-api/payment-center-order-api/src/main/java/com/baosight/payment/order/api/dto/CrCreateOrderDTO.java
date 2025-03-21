package com.baosight.payment.order.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 码牌创建订单
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Data
public class CrCreateOrderDTO {
    /**
     * 渠道订单编号
     */
    private String channelOrderNo;

    /**
     * 交易账号 微信则是openId, 支付宝则是userId
     */
    private String tradeUser;

    /**
     * 商户渠道账号表示 如何微信AppId
     */
    private String mchChannelUser;

    /**
     * 交易时间
     */
    private Date createTime;

    /**
     * 是否参与分账
     */
    private Boolean hasDivision;


    /**
     * 商户扩展参数
     */
    private String extParam;

    /**
     * 异步通知地址
     */
    private String notifyUrl;


    /**
     * 原交易流水号
     */
    private String outTradeNo;

    /**
     * 页面跳转地址
     */
    private String returnUrl;

    /**
     * 交易金额
     */
    private Long totalAmount;

    /**
     * 完成时间
     */
    private Date finishTime;

    private Long appId;

    private Long mchId;

    private String mchNo;

    /**
     * 支付方式编码
     */
    private String interfaceCode;

    /**
     * 商户名称
     */
    private String mchName;

    private Integer mchType;

    private Long isvId;

    /**
     * 支付方式code
     */
    private String wayCode;
    /**
     * 手续费
     */
    private Long mchFeeRate;

    /**
     * 订单状态
     */
    private Integer state;
}
