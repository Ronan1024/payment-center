package com.baosight.payment.order.pojo.vo;

import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@Data
public class PayOrderPageVO {
    /**
     * 支付订单号
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long orderId;
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商户id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long mchId;
    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 服务商id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long isvId;

    /**
     * 服务商号
     */
    private String isvNo;

    /**
     * 商户名称
     */
    private String mchName;

    /**
     * 商户类型
     */
    private Integer mchType;

    /**
     * 商户订单号
     */
    private String mchOrderNo;

    /**
     * 渠道代码
     */
    private String channelCode;
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
     * 三位货币代码,人民币:cny
     */
    private String currency;

    /**
     * 支付订单状态
     */
    private Integer state;

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

    /**
     * 通知状态
     */
    private Integer notifyState;
}
