package com.baosight.payment.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description: 商户异常信息
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
public enum MchError implements IErrorEnum<String> {
    /**
     *商户不存在
     */
    MCH_NOT_FOUND("10001", "商户不存在"),
    /**
     * 商户类型异常
     */
    MCH_TYPE_ERROR("10002", "商户类型异常"),
    ;

    MchError(String code, String msg) {
        initEnum(code, msg);
    }
}
