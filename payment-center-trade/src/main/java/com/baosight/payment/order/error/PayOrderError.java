package com.baosight.payment.order.error;


import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/24
 */

public enum PayOrderError implements IErrorEnum<String> {
    /**
     * 订单不存在
     */
    ORDER_NOT_FOUND("10001", "订单不存在"),
    ;

    PayOrderError(String code, String msg){
        initEnum(code, msg);
    }
}
