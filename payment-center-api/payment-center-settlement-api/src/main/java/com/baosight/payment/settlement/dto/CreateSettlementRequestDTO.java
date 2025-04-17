package com.baosight.payment.settlement.dto;

import cn.hutool.core.math.Money;
import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@Data
public class CreateSettlementRequestDTO {
    /**
     * 结算类型
     */
    private String type;

    /**
     * 交易金额
     */
    private Money amount;

    /**
     * 交易确认时间
     */
    private Date firmTime;

    /**
     * 结算金额
     */
    private Money settleAmount;

    /**
     * 交易关联id
     */
    private Long orderId;
}
