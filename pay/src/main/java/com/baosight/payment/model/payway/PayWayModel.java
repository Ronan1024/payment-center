package com.baosight.payment.model.payway;


import lombok.Data;

/**
 * @author L.J.Ran
 */
@Data
public class PayWayModel {

    private String id;

    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 支付图标
     */
    private String icon;
}
