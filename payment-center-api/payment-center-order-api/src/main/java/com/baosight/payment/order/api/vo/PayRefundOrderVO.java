package com.baosight.payment.order.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 退款支付订单信息
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Data
public class PayRefundOrderVO {
    /**
     * 退款id
     */
    private Long id;

    /**
     * 支付订单号（与pay_order对应）
     */
    private Long payOrderId;

    /**
     * 渠道支付单号（与pay_order channel_order_no对应）
     */
    private String channelPayOrderNo;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 服务商号
     */
    private String isvNo;

    /**
     * 应用编号
     */
    private String appNo;

    /**
     * 商户名称
     */
    private String mchName;

    /**
     * 类型: 1-普通商户, 2-特约商户(服务商模式)
     */
    private Integer mchType;

    /**
     * 商户退款单号（商户系统的订单号）
     */
    private String mchRefundNo;

    /**
     * 支付方式代码
     */
    private String payWayCode;

    /**
     * 支付接口代码
     */
    private String interfaceCode;

    /**
     * 支付金额,单位分
     */
    private Long payAmount;

    /**
     * 退款金额,单位分
     */
    private Long refundAmount;

    /**
     * 三位货币代码,人民币:cny
     */
    private String currency;

    /**
     * 退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败,4-退款任务关闭
     */
    private Integer state;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 渠道订单号
     */
    private String channelOrderNo;

    /**
     * 渠道错误码
     */
    private String errCode;

    /**
     * 渠道错误描述
     */
    private String errMsg;

    /**
     * 特定渠道发起时额外参数
     */
    private String channelExtra;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 扩展参数
     */
    private String extParam;

    /**
     * 订单退款成功时间
     */
    private Date successTime;

    /**
     * 退款失效时间（失效后系统更改为退款任务关闭状态）
     */
    private Date expiredTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 支付订单编号
     */
    private String payOrderNo;

    /**
     * 原始商户支付订单号
     */
    private String originMchPayOrderNo;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 服务商id
     */
    private Long isvId;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 接口id
     */
    private Long interfaceId;

    /**
     * 支付方式id
     */
    private Long payWayId;

    /**
     * 系统退款流水号
     */
    private String refundNo;

    /**
     * 渠道返回信息
     */
    private String chanelResult;
}
