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
     * 订单类型
     */
    private Integer orderType;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 支付时间
     */
    private Long payTime;
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
    private Long payAmount;

    /**
     * 服务商号
     */
    private String isvNo;

    /**
     * 支付类型
     */
    private Integer payType;
}
