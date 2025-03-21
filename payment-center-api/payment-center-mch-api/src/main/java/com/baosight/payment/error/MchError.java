package com.baosight.payment.error;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 商户异常信息
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
public enum MchError implements IBaseEnum<String> {
    /**
     *商户不存在
     */
    MCH_NOT_FOUND("10001", "商户不存在"),
    ;

    MchError(String code, String msg) {
        initEnum(code, msg);
    }
}
