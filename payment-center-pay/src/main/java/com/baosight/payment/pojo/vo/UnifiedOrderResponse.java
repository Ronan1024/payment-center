package com.baosight.payment.pojo.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/28
 */
@Data
public class UnifiedOrderResponse<T> {
    /**
     * 支付订单号
     **/
    private String payOrderId;

    /**
     * 商户订单号
     **/
    private String mchOrderNo;

    /**
     * 订单状态
     **/
    private Integer orderState;


    /**
     * 支付参数类型  ( 无参数，  调起支付插件参数， 重定向到指定地址，  用户扫码   )
     **/
    private String payDataType;

    /**
     * 支付参数
     **/
    private String payData;

    /**
     * 渠道返回错误代码
     **/
    private String errCode;

    /**
     * 渠道返回错误信息
     **/
    private String errMsg;

    /**
     * 渠道参数信息
     */
    private T channelFrontParamInfo;
}
