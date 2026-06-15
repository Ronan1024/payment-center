package com.baosight.payment.enums;


import com.ronan.common.enums.IBaseEnum;

/**
 * 签约模式枚举
 */
public enum ModeCode implements IBaseEnum<String> {

    /**
     * 直连模式：商户自己直接和微信、支付宝、银行或三方支付机构签约。
     */
    DIRECT("DIRECT", "直连模式"),

    /**
     * 服务商模式：平台或服务商拥有服务商资质，下面挂多个特约商户 / 子商户。
     */
    SERVICE_PROVIDER("SERVICE-PROVIDER", "服务商模式"),

    /**
     * 子商户模式: 第三方支付机构，比如通联、拉卡拉、富友、随行付,
     */
    SUB_MERCHANT("SUB-MERCHANT", "子商户模式"),

    /**
     * 平台统一签约、统一收款，业务上再内部结算给商户
     */
    PLATFORM("PLATFORM", "平台模式"),


    /**
     * MOCK 本地联调、测试环境、内部余额支付
     */
    MOCK("MOCK", "内部模式"),
    ;

    ModeCode(String code, String msg) {
        initEnum(code, msg);
    }

}
