package com.baosight.payment.vo;

import lombok.Data;

import java.util.Map;

/**
 * @program: payment-center
 * @description: 商户接口配置信息
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Data
public class IsvInterfaceConfigVO {

    /**
     * 接口id
     */
    private Long interfaceId;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 支付接口编号
     */
    private String interfaceCode;

    /**
     * 配置信息
     */
    private Map<String, String> config;

    /**
     * 商户号
     */
    private String isvNo;

    /**
     * 商户id
     */
    private Long isvId;


}
