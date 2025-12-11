package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
public enum DivisionState implements IBaseEnum<Integer> {
    /**
     * 未发生分账
     */
    NOT_DIVISION(0, "未发生分账"),
    /**
     * 等待分账任务处理
     */
    WAITING(1, "等待分账任务处理"),
    /**
     * 分账处理中
     */
    DIVISION_ING(2, "分账处理中"),

    /**
     * 分账成功
     */
    DIVISION_SUCCESS(3, "分账成功"),

    /**
     * 分账失败
     */
    DIVISION_FAILURE(4, "分账失败"),
    /**
     * 渠道处理完成
     */
    CHANNEL_HANDLER_SUCCESS(5, "渠道处理完成"),
    /**
     * 渠道处理失败
     */
    CHANNEL_HANDLER_FAILURE(6, "渠道处理失败"),

    /**
     * 渠道处理中
     */
    DIVISION_CHANNEL_PROCESSING(7, "渠道处理中")

    ;


    DivisionState(Integer code, String msg) {
        initEnum(code, msg);
    }
}
