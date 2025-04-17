package com.baosight.payment.settlement.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 结算受理单
 *
 * @author L.J.Ran
 * @TableName settlement_request
 */
@Data
@TableName(value = "settlement_request")
public class SettlementRequest {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 结算状态
     */
    private Integer state;

    /**
     * 结算类型
     */
    private String type;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 交易确认时间
     */
    private Date createTime;

    /**
     * 结算金额
     */
    private BigDecimal settleAmount;

    /**
     * 交易关联id
     */
    private Long orderId;
}