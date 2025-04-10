package com.baosight.payment.model.order;

import lombok.Data;

/**
 * 统一下单请求体
 *
 * @author L.J.Ran
 */
@Data
public class UnifiedOrder {
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 请求流水号
     */
    private String requestId;
    /**
     * 应用id
     */
    private String appId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

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
     * 签名
     */
    private String sign;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 接口版本
     */
    private String version;

    /**
     * 通知接口
     */
    private String notifyUrl;


    /**
     * 跳转通知地址
     **/
    private String returnUrl;


    /**
     * 特定渠道发起额外参数
     */
    private String channelExtra;


    /**
     * 支付方式
     */
    private String wayCode;

    /**
     * 支付方式币种
     */
    private String currency;

    /**
     * 商品标题
     */
    private String subject;

    /**
     * 商品描述信息
     */
    private String body;


    /**
     * 商户扩展参数
     */
    private String extParam;

    /**
     * 订单失效时间
     */
    private Integer expiredTime;

    /**
     * 分账模式
     */
    private Integer divisionMode;

    /**
     * 下游用户
     */
    private String signUser;
}
