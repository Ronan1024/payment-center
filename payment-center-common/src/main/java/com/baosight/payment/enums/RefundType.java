package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
public enum RefundType implements IBaseEnum<Integer> {
    /**
     * 未发生退款
     */
    REFUND_TYPE_NONE(0, "未发生退款"),
    /**
     * 部分退款
     */
    REFUND_TYPE_SUB(1, "部分退款"),
    /**
     * 全额退款
     */
    REFUND_TYPE_ALL(2, "全额退款")
    ;

    RefundType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
