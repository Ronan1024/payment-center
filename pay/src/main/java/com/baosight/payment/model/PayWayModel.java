package com.baosight.payment.model;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public PayWayModel(String id, String payName, String icon) {
        this.id = id;
        this.payName = payName;
        this.icon = icon;
    }

    public PayWayModel() {
    }
}
