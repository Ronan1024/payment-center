package com.baosight.payment.settlement.dto;

import lombok.Data;

import java.util.Date;

/**
 * 注册结算账单流水
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@Data
public class RegisterSettlementFlowDTO {

    private Long id;

    /**
     * 结算受理单
     */
    private Long requestId;

    /**
     * 结算账期ID(当归集后进行填充)
     */
    private Long periodId;

    /**
     * 结算金额
     */
    private Integer amount;

    /**
     * 手续费
     */
    private Integer fee;

    /**
     * 结算流水类型
     */
    private Integer type;

    /**
     * 结算流水状态
     */
    private Integer state;

    /**
     * 结算时间
     */
    private Date settleTime;
}
