package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Getter
public enum RefundOrderState implements IBaseEnum<Integer> {
    /**
     * 订单生成
     */
    ORDER_GENERATED(0, "订单生成", "CREATE"),
    /**
     * 退款中
     */
    REFUNDING(1, "退款中", "REFUNDING"),
    /**
     * 退款成功
     */
    REFUNDED(2, "退款成功", "SUCCESS"),
    /**
     * 退款失败
     */
    REFUND_FAILED(3, "退款失败", "FAIL"),
    /**
     * 退款任务关闭
     */
    REFUND_TASK_CLOSED(4, "退款任务关闭", "CLOSE");


    private final String state;

    RefundOrderState(Integer code, String msg, String state) {
        initEnum(code, msg);
        this.state = state;
    }
}
