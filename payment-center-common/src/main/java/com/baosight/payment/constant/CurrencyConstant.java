package com.baosight.payment.constant;

import java.util.Currency;

/**
 * 币种常量信息
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
public class CurrencyConstant {
    // 美元
    public static final Currency USD = Currency.getInstance("USD");
    // 人民币
    public static final Currency CNY = Currency.getInstance("CNY");
    // 欧元
    public static final Currency EUR = Currency.getInstance("EUR");
    // 英镑
    public static final Currency GBP = Currency.getInstance("GBP");
    // 日元
    public static final Currency JPY = Currency.getInstance("JPY");
    // 澳元
    public static final Currency AUD = Currency.getInstance("AUD");
    // 加元
    public static final Currency CAD = Currency.getInstance("CAD");
    // 港币
    public static final Currency HKD = Currency.getInstance("HKD");
    // 新加坡元
    public static final Currency SGD = Currency.getInstance("SGD");
    // 韩元
    public static final Currency KRW = Currency.getInstance("KRW");

    private CurrencyConstant() {
        throw new IllegalArgumentException("Utility class");
    }
}
