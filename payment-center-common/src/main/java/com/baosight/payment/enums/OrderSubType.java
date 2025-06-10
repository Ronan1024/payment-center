package com.baosight.payment.enums;


import com.baosight.utils.enums.IBaseEnum;

/**
 * 子订单类型
 *
 * @author L.J.Ran
 */
public enum OrderSubType implements IBaseEnum<Integer> {
    /**
     * 消费订单
     */
    CONSUMPTION(1, "消费订单"),
    /**
     * 商户收款
     */
    MERCHANT_COLLECTION(2, "商户收款"),

    /**
     * 预消费
     */
    PRE_CONSUMPTION(3, "预消费"),
    /**
     * 微信预消费
     */
    WECHAT_PRE_CONSUMPTION(4, "微信预消费"),


    /**
     * 订单完成
     */
    ORDER_COMPLETED(5, "订单完成"),

    /**
     * 微信订单完成
     */
    WECHAT_ORDER_COMPLETED(6, "微信订单完成"),


    /**
     * 退款
     */
    REFUND(7, "退款订单"),

    /**
     * 商户提现
     */
    MERCHANT_WITHDRAWAL(8, "商户提现"),

    /**
     * 结算
     */
    SETTLEMENT(9, "结算订单"),
    /**
     * 差错处理渠道多帐
     */
    ERROR_CHANNEL_MULTIPLE_ACCOUNTS(10, "差错处理渠道多帐"),
    /**
     * 差错处理渠道少账
     */
    ERROR_CHANNEL_INSUFFICIENT_ACCOUNTS(11, "差错处理渠道少账"),

    ;

    OrderSubType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
