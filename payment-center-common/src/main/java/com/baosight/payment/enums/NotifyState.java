package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 通知状态
 * @author: L.J.Ran
 * @create: 2025/3/20
 */

public enum NotifyState implements IBaseEnum<Integer> {
    /**
     * 通知中
     */
    NOTIFIED(1, "通知中"),
    ;
    NotifyState(Integer code, String msg){
        initEnum(code, msg);
    }
}
