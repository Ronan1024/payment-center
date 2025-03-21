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
public class MchInterfaceConfigVO {

    /**
     * 接口id
     */
    private Long interfaceId;


    /**
     * 商户类型
     */
    private Integer mchType;

    /**
     * 配置信息
     */
    private Map<String, String> config;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 商户id
     */
    private Long mchId;


    /**
     * 三方系统code
     */
    private String thirdCode;

    /**
     * 手续费
     */
    private Long mchFeeRate;

}
