package com.baosight.payment.system.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayingAgencyVO {
    /**
     * 支付机构代码
     */
    private String code;

    /**
     * 支付机构名称
     */
    private String name;
}
