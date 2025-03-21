package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

public enum State implements IBaseEnum<Integer> {
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 禁用
     */
    FORBIDDEN(2, "禁用");

    State(Integer code, String msg) {
        initEnum(code, msg);
    }
}
