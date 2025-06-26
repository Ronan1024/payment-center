package com.baosight.payment.settlement.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算账期
 * @author L.J.Ran
 * @TableName settlement_period
 */
@TableName(value ="settlement_period")
@Data
public class SettlementPeriod {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 商户di
     */
    private Long mchId;

    /**
     * 账期开始时间
     */
    private Date startTime;

    /**
     * 账期结束时间
     */
    private Date endTime;

    /**
     * 账期状态
     */
    private Integer state;

    /**
     * 账期总金额
     */
    private BigDecimal totalAmount;
}