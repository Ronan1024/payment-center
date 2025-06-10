package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * 产品类型 以0开头3位类型
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/5
 */
public enum ProductType implements IBaseEnum<String> {
    /**
     * 支付-线上支付
     */
    ONLINE_PAYMENT("001", "支付-线上支付"),
    /**
     * 支付-线下支付
     */
    OFFLINE_PAYMENT("002", "支付-线下支付"),
    ;

    ProductType(String code, String msg) {
        initEnum(code, msg);
    }
}
