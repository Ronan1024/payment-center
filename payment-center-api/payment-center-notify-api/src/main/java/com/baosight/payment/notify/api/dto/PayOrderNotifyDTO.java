package com.baosight.payment.notify.api.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 订单支付成功通知
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Data
public class PayOrderNotifyDTO {
    /**
     * 异步通知地址
     */
    private String notifyUrl;

    /**
     * 支付流水号
     */
    private Long orderId;

    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 应用id
     */
    private Long appId;

    private Long payType;

    /**
     * 订单完成时间
     */
    private Date finishTime;

}
