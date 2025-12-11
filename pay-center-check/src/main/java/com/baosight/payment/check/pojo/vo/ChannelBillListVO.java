package com.baosight.payment.check.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelBillListVO extends BasePO {

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 对账文件id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long billFileId;

    /**
     * 对账文件code
     */
    private String billFileCode;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 支付渠道id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long channelId;

    /**
     * 支付渠道code
     */
    private String channelCode;

    /**
     * 接口名称
     */
    private String channelName;

    /**
     * 二级渠道id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long subChannelId;

    /**
     * 二级渠道code
     */
    private String subChannelCode;

    /**
     * 平台订单id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long orderId;

    /**
     * 交易类型
     */
    private Integer tradeType;
    /**
     * 渠道订单号
     */
    private String channelOrderId;


    /**
     * 交易时间
     */
    private Date tradingTime;


    /**
     * 渠道订单状态
     */
    private Integer tradingState;

    /**
     * 渠道交易金额
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
     * 手续费明细
     */
    private String tradingFeeDetail;

    /**
     * 手续费规则
     */
    private String tradingFeeRule;

    /**
     * 渠道商户号
     */
    private String channelMchNo;


    /**
     * 渠道用户标识
     */
    private String  userNo;

    /**
     * 渠道原始订单号， 预留退款
     */
    private String channelOriginOrder;

    /**
     * 渠道原始交易类型
     */
    private String channelTradingOriginType;


    /**
     * 渠道交易原始数据
     */
    private String meteDate;


    /**
     * 账单状态
     */
    private Integer billState;

}
