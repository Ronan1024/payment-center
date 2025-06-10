package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;
import lombok.Getter;

/**
 * 交易模式
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/5
 */
@Getter
public enum TradeModel implements IBaseEnum<String> {
    /**
     * 微信小程序
     */
    WECHAT_MINI_PROGRAM("4011", "WECHAT_PAY_MINI_PROGRAM", "微信小程序支付"),
    /**
     * 微信正扫
     */
    WECHAT_SCAN("4012", "WECHAT_PAY_SCAN", "微信正扫支付"),
    ;
    private final String desc;

    TradeModel(String code, String name, String desc) {
        initEnum(code, name);
        this.desc = desc;
    }
}
