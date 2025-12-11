package com.baosight.payment.enums;


import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 通知状态
 * @author: L.J.Ran
 * @create: 2025/3/20
 */

public enum NotifyState implements IBaseEnum<Integer> {
    /**
     * 无需通知
     */
    NONE(0, "无需通知"),
    /**
     * 通知中
     */
    NOTIFIED(1, "通知中"),
    /**
     * 通知成功
     */
    SUCCESS(2, "通知成功"),
    /**
     * 通知失败
     */
    FAIL(3, "通知失败"),
    /**
     * 通知处理中
     */
    PROCESSING(4, "通知处理中"),
    ;

    NotifyState(Integer code, String msg) {
        initEnum(code, msg);
    }

    /**
     * 是否增加通知次数
     */
    public boolean isIncreaseNotifyCount() {
        NotifyState notifyState = this;
        return notifyState.equals(NotifyState.SUCCESS) || notifyState.equals(NotifyState.FAIL) || notifyState.equals(NotifyState.NOTIFIED);
    }
}
