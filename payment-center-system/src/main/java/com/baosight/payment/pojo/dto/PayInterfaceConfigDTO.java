package com.baosight.payment.pojo.dto;

import lombok.Data;

@Data
public class PayInterfaceConfigDTO {
    private Long id;

    /**
     * 支付接口id
     */
    private Long payInterfaceId;

    /**
     * 服务商或商家id
     */
    private Long clientId;

    /**
     * 支付接口参数
     */
    private String payInterfaceParams;

    /**
     * 支付费率
     */
    private Long payRate;

    /**
     * 备注
     */
    private String remark;
}
