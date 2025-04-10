package com.baosight.payment.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
@Data
public class MchAppPassageVO {

    /**
     * 商户号
     */
    private Long mchId;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 支付接口
     */
    private Long interfaceId;

    /**
     * 支付接口编号
     */
    private String interfaceCode;

    /**
     * 支付方式id
     */
    private Long payWayId;

    /**
     * 支付方式
     */
    private String payWayCode;

    /**
     * 支付方式费率
     */
    private Long rate;


}
