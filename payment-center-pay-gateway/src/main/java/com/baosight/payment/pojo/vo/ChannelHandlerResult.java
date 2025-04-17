package com.baosight.payment.pojo.vo;

import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/30
 */
@Data
public class ChannelHandlerResult {
    /**
     * 渠道错误码
     **/
    private String channelErrCode;

    /**
     * 渠道错误描述
     **/
    private String channelErrMsg;

    /**
     * 渠道状态待处理
     */
    private Integer channelState;

    /**
     * 上游渠道返回的原始报文, 一般用于[运营平台的查询上游结果]功能
     **/
    private String channelOriginResponse;


    /**
     * 渠道支付数据包, 一般用于支付订单的继续支付操作
     **/
    private String channelAttach;

    /**
     * 交易类型
     */
    private String tradingType;
    /**
     * 交易模式
     */
    private Integer tradingModel;

    /**
     * 渠道商户号
     */
    private String channelMchNo;


    private ResponseEntity responseEntity;

}
