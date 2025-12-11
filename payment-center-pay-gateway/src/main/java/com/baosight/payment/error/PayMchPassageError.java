package com.baosight.payment.error;

import com.baosight.web.core.exception.IErrorEnum;


public enum PayMchPassageError implements IErrorEnum<String> {
    /**
     * 商户应用不支持该支付方式
     */
    MCH_APP_NONSUPPORT_PAY_WAY("20001", "商户应用不支持该支付方式"),
    ;

    PayMchPassageError(String code, String msg) {
        initEnum(code, msg);
    }
}
