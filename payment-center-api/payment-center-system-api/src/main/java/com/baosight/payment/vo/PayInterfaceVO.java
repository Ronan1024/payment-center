package com.baosight.payment.vo;

import lombok.Data;

@Data
public class PayInterfaceVO {

    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 是否支持普通商户
     */
    private Boolean hasMch;

    /**
     * 是否支持服务商模式
     */
    private Boolean hasIsvMch;

    /**
     * 服务商支付参数配置
     */
    private String isvParams;

    /**
     * 特约商户配置
     */
    private String isvSubMchParams;

    /**
     * 普通商户支付参数配置
     */
    private String normalMchParams;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付方式列表使用, 分割
     */
    private String payWay;

    /**
     * 支付机构
     */
    private String payingAgency;
}
