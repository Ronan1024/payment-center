package com.baosight.payment.pojo.dao;

import com.baosight.payment.enums.PayWayCode;
import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/28
 */
@Data
public class ParseChannelParamDAO {
    /**
     * 渠道状态
     */
    private String channelState;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 错误编号
     */
    private String errCode;
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 渠道订单号
     */
    private String channelOrderId;
    /**
     * 商户编号
     */
    private Long mchId;
    /**
     * 订单完成时间
     */
    private Date finishTime;
    /**
     * 订单状态
     */
    private Integer orderState;

    /**
     * 渠道用户id
     */
    private String channelUserId;

    /**
     * 渠道返回信息
     */
    private String channelResult;

    /**
     * 支付机构
     */
    private Integer payingAgency;
    /**
     * 支付方式
     */
    private PayWayCode payWayCode;

    /**
     * 订单类型
     */
    private String type;

    /**
     * 子订单类型
     */
    private Integer subType;



    /**
     * 支付机构渠道支付orderId
     */
    private String payAgencyChannelOrder;
}
