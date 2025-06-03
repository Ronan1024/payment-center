package com.baosight.payment.constant;

/**
 * rocket 延时消息常量
 * 1s、 5s、 10s、 30s、 1m、 2m、 3m、 4m、 5m、 6m、 7m、 8m、 9m、 10m、 20m、 30m、 1h、 2h
 */
public class DelayLevel {

    private DelayLevel() {
        throw new UnsupportedOperationException("This is a constant class");
    }

    /**
     * 1S
     */
    public static final Integer ONE_SECOND = 1;

    /**
     * 5S
     */
    public static final Integer FIVE_SECOND = 2;

    /**
     * 10S
     */
    public static  final Integer TEN_SECOND = 3;

    /**
     * 30S
     */
    public static final Integer THIRTY_SECOND = 4;

    /**
     * 1M
     */
    public static  final Integer ONE_MINUTE = 5;

    /**
     * 2M
     */
    public static final  Integer TWO_MINUTE = 6;

    /**
     * 3M
     */
    public static  final Integer THREE_MINUTE = 7;

    /**
     * 4M
     */
    public static final  Integer FREE_MINUTE = 8;

    /**
     * 5M
     */
    public static  final Integer FIVE_MINUTE = 9;

    /**
     * 6m
     */
    public static  final Integer SIX_MINUTE = 10;

    /**
     * 7M
     */
    public static final  Integer SEVEN_MINUTE = 11;

    /**
     * 8M
     */
    public static  final Integer EIGHT_MINUTE = 12;

    /**
     * 9M
     */
    public static  final Integer NINE_MINUTE = 13;

    /**
     * 10m
     */
    public static  final Integer TEN_MINUTE = 14;

    /**
     * 20m
     */
    public static  final Integer TWENTY_MINUTE = 15;

    /**
     * 30m
     */
    public static final Integer THIRTY_MINUTE = 16;

    /**
     * 1h
     */
    public static final Integer ONE_HOUR = 17;

    /**
     * 2h
     */
    public static final Integer TWO_HOUR = 18;
}
