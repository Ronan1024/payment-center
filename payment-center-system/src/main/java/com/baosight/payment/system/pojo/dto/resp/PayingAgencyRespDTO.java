package com.baosight.payment.system.pojo.dto.resp;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
@Data
public class PayingAgencyRespDTO {

    /**
     * 支付机构id
     */
    private Integer id;

    /**
     * 支付机构名称
     */
    private String name;

    /**
     * 支付机构编号
     */
    private String code;
}
