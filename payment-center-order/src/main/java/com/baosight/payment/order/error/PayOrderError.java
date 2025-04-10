package com.baosight.payment.order.error;


import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/24
 */

public enum PayOrderError implements IBaseEnum<String> {
    /**
     * 订单不存在
     */
    ORDER_NOT_FOUND("10001", "订单不存在"),
    ;

    PayOrderError(String code, String msg){
        initEnum(code, msg);
    }
}
