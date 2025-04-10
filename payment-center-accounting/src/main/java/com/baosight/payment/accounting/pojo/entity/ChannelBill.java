package com.baosight.payment.accounting.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 渠道账单
 *
 * @author l.J.Ran
 * @TableName channel_bill
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "channel_bill")
public class ChannelBill extends BasePO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账单文件id
     */
    private Long billFileId;

    /**
     * 账单文件编号
     */
    private String billFileCode;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 渠道id
     */
    private Long channelId;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 二级渠道id 如接入通联 -> 通联接入 微信
     */
    private Long subChannelId;

    /**
     * 二级渠道编号
     */
    private String subChannelCode;

    /**
     * 平台订单id
     */
    private Long orderId;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 渠道订单号
     */
    private String channelOrderId;

    /**
     * 交易时间
     */
    private Date tradingTime;

    /**
     * 渠道商户号
     */
    private String channelMchNo;

    /**
     * 渠道原始订单号， 预留退款时
     */
    private String channelOriginOrder;

    /**
     * 渠道用户标识
     */
    private String userNo;

    /**
     * 渠道交易原始类型
     */
    private Integer channelTradingOriginType;

    /**
     * 交易原始数据
     */
    private String meteDate;

    /**
     * 交易状态
     */
    private Integer tradingState;

    /**
     * 交易金额
     */
    private Long tradingAmount;

    /**
     * 交易币种
     */
    private String currency;

    /**
     * 交易手续费
     */
    private Long tradingFee;

    /**
     * 交易手续费详情
     */
    private String tradingFeeDetail;

    /**
     * 交易规则
     */
    private String tradingFeeRule;

    /**
     * 结算金额
     */
    private Long settlementAmount;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 原始数据
     */
    private String sourceDate;

    /**
     * 账单状态
     */
    private Integer billState;
}