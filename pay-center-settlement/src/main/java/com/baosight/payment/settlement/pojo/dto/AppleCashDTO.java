package com.baosight.payment.settlement.pojo.dto;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */
@Data
public class AppleCashDTO {
    /**
     * 商户id
     */
    private String mchNo;

    /**
     * 申请订单id
     */
    private String applyOrderId;

    private String amount;

    /**
     * 回调地址
     */
    private String notifyUrl;
}
