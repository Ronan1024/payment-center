package com.baosight.payment.channel.pojo.dao;


import lombok.Data;

import java.util.Map;

@Data
public class TongLianOrderResultNotifyDTO {

    /**
     * 订单状态
     * SUCCESS / FAIL（以对方文档为准）
     */
    private String result;

    /**
     * 错误信息
     * 订单状态为“交易失败”时有值
     */
    private String respMsg;

    /**
     * 商户订单号（支付订单）
     */
    private String reqTraceNum;

    /**
     * 通联订单号
     */
    private String respTraceNum;

    /**
     * 原云商通订单号
     * 仅退款订单返回
     */
    private String orgRespTraceNum;

    /**
     * 原商户订单号
     * 仅退款订单返回
     */
    private String orgReqTraceNum;

    /**
     * 订单金额（单位：分）
     */
    private Long orderAmount;

    /**
     * 订单支付完成时间
     * 格式：yyyy-MM-dd HH:mm:ss
     */
    private String finishTime;

    /**
     * 商户会员编号 - 付款人
     */
    private String signNum;

    /**
     * 商户会员编号 - 收款人
     * 仅消费和转账
     */
    private String receiverSignNum;

    /**
     * 退款资金调拨结果
     * 0：调拨失败
     * 1：调拨成功
     */
    private String transferResult;

    /**
     * 调拨金额（单位：分）
     */
    private Long transferAmount;

    /**
     * 扩展参数
     * 原样返回，不可包含“|”
     */
    private String extendParams;

    /**
     * 渠道参数信息（支付详情）
     * 字段不固定，使用 Map 接收
     */
    private Map<String, Object> channelParamInfo;
}
