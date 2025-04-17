package com.baosight.payment.settlement.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 账单流水
 *
 * @author L.J.Ran
 * @TableName account_flow
 */
@Data
@TableName(value = "account_flow")
public class AccountFlow {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 结算受理单
     */
    private Long requestId;

    /**
     * 交易类型
     */
    private Integer tradingType;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 交易时间
     */
    private Date tradingTime;

    /**
     * 渠道交易id
     */
    private String channelTradingId;


    /**
     * 账单日期
     */
    private Date date;

    /**
     * 渠道结算金额
     */
    private BigDecimal channelSettlementAmount;

    /**
     * 渠道手续费
     */
    private BigDecimal channelFee;
    /**
     * 渠道费率
     */
    private Long channelFeeRate;
    /**
     * 原订单id
     */
    private Long originalOrderId;

    /**
     * 原渠道交易id
     */
    private String originalChannelTradingId;
}