package com.baosight.payment.channel.handler.config.allin;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
@Data
public class AllInPayMchConfig {
    /**
     * 通联商户私钥
     */
    private String signNum;

    /**
     * 商户名称
     */
    private String signName;

    /**
     * 商户号
     */
    @SuppressWarnings("all")
    private String cusid;
}
