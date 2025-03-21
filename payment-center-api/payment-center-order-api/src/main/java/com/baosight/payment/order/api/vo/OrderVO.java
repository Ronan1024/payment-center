package com.baosight.payment.order.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 订单信息
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Data
public class OrderVO {
    /**
     * 支付订单号
     */
    private Long id;

    /**
     * 订单流水
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
     * 支付金额,单位分
     */
    private Long amount;

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
     * 退款状态: 0-未发生实际退款, 1-部分退款, 2-全额退款
     */
    private Integer refundState;

    /**
     * 退款次数
     */
    private Integer refundTimes;

    /**
     * 退款总金额,单位分
     */
    private Long refundAmount;

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
     * 最新分账时间
     */
    private Date divisionLastTime;

    /**
     * 渠道支付错误码
     */
    private String errCode;

    /**
     * 渠道支付错误描述
     */
    private String errMsg;

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
     * 订单支付成功时间
     */
    private Date successTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
