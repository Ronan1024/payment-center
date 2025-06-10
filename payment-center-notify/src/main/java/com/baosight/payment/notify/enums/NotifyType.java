package com.baosight.payment.notify.enums;

import com.baosight.common.enums.IBaseEnum;

/**
 * 通知类型
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/5
 */
public enum NotifyType implements IBaseEnum<Integer> {
    /**
     * 线下支付
     */
    OFFLINE_PAY(1, "线下支付"),
    /**
     * 线上支付
     */
    ONLINE_PAY(2, "线上支付"),
    ;

    NotifyType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
