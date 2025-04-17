package com.baosight.payment.check.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 交易流水 注册至对账中心
 * @author L.J.Ran
 * @TableName trading_flow
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="trading_flow")
public class TradingFlow extends BasePO {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 交易金额
     */
    private Long amount;

    /**
     * 渠道成本
     */
    private Long channelCost;

    /**
     * 可结算金额(根据渠道成本进行计算)
     */
    private Long canSettleAmount;

    /**
     * 交易类型
     */
    private String tradingType;

    /**
     * 交易流水状态
     */
    private Integer state;

    /**
     * 交易状态
     */
    private Integer tradingState;
    /**
     * 交易模式
     */
    private Integer tradingMode;

    /**
     * 交易订单id
     */
    private Long orderId;

    /**
     * 交易日期
     */
    private String date;

    /**
     * 渠道订单号
     */
    private String channelOrderId;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 交易时间(完成时间)
     */
    private Date tradingTime;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 渠道商户编号
     */
    private String channelMchNo;

    /**
     * 原始订单id(退款时才会(存在值)
     */
    private Long originOrderId;
}