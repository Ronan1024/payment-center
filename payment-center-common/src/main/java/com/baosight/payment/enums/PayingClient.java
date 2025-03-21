package com.baosight.payment.enums;


import com.baosight.common.enums.IBaseEnum;

import java.util.Arrays;

/**
 * @author L.J.Ran
 */

public enum PayingClient implements IBaseEnum<Integer> {
    /**
     * pc 支付
     */
    PC(1, "PC 支付", "PC"),
    /**
     * app 支付
     */
    APP(2, "APP 支付", "APP"),
    /**
     * 微信小程序
     */
    WX_MINI_PROGRAM(3, "微信小程序", "WX_MINI_PROGRAM"),
    /**
     * h5支付
     */
    H5(4, "H5 支付", "H5"),
    /**
     * 移动端
     */
    MOBILE(5, "移动端", "MOBILE"),
    /**
     * 线下支付
     */
    OFFLINE(6, "线下支付", "OFFLINE")
    ;

    private final String name;

    PayingClient(Integer code, String msg, String name) {
        initEnum(code, msg);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PayingClient payingClient(String name) {
        return Arrays.stream(PayingClient.values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }
}
