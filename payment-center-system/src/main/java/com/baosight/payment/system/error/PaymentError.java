package com.baosight.payment.system.error;

import com.ronan.common.enums.IBaseEnum;

public enum PaymentError implements IBaseEnum<String> {
    /**
     * 参数不能为空
     */
    PARAMS_NOT_NULL("100001", "{}")
    ;

    PaymentError(String code, String msg) {
        initEnum(code, msg);
    }
}
