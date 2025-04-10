package com.baosight.payment.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AbstractMchApp extends Abstract{
    /**
     * 商户号
     **/
    private String mchNo;

    /**
     * 商户应用ID
     **/
    private String appId;
}
