package com.baosight.payment.order.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 创建订单DTO
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
@Data
public class CreateOrderDTO {

    private Long orderId;
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商户id
     */
    private Long mchId;
    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 服务商id
     */
    private Long isvId;

    /**
     * 服务商号
     */
    private String isvNo;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 应用编号
     */
    private String appNo;
    /**
     * 商户名称
     */
    private String mchName;

    /**
     * 类型
     */
    private Integer mchType;

    /**
     * 商户订单号
     */
    private String mchOrderNo;

    /**
     * 支付接口代码
     */
    private String ifCode;

    /**
     * 支付方式代码
     */
    private String wayCode;

    /**
     * 支付总金额 单位分 = payAmount + promotionAmount
     */
    private Long totalAmount;
    /**
     * 支付金额
     */
    private Long payAmount;

    /**
     * 营销金额
     */
    private Long promotionAmount;

    /**
     * 商户手续费费率快照
     */
    private Long mchFeeRate;

    /**
     * 商户手续费,单位分
     */
    private Long mchFeeAmount;

    /**
     * 三位货币代码,人民币:cny
     */
    private String currency;

    /**
     * 支付订单状态
     */
    private Integer state;

    /**
     * 向下游回调状态
     */
    private Integer notifyState;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 商品标题
     */
    private String subject;

    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 特定渠道发起额外参数
     */
    private String channelExtra;

    /**
     * 渠道用户标识,如微信openId,支付宝账号
     */
    private String channelUser;

    /**
     * 渠道订单号
     */
    private String channelOrderNo;


    /**
     * 是否参与分账
     */
    private Boolean hasDivision;

    /**
     * 订单分账模式
     */
    private Integer divisionMode;

    /**
     * 订单分账状态
     */
    private Integer divisionState;


    /**
     * 商户扩展参数
     */
    private String extParam;

    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 页面跳转地址
     */
    private String returnUrl;

    /**
     * 订单失效时间
     */
    private Date expiredTime;

    /**
     * 订单创建时间
     */
    private Date createTime;


    /**
     * 下游用户
     */
    private String signUser;

    /**
     * 交易类型
     */
    private String tradingType;

    /**
     * 交易模式
     */
    private Integer tradingMode;

    /**
     * 渠道商户号
     */
    private String channelMchNo;
}
