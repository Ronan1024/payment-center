package com.baosight.payment.system.error;


import com.baosight.utils.enums.IBaseEnum;

public enum PayWayError implements IBaseEnum<String> {
    /**
     * 支付方式不存在
     */
    PAY_WAY_NOT_FOUND("100001", "支付方式不存在"),
    /**
     * 支付code已存在
     */
    PAY_WAY_CODE_EXIST("100002", "支付方式code已存在"),
    /**
     * 支付机构信息异常
     */
    PAY_WAY_AGENCY_ERROR("100003", "支付机构信息异常"),
    ;

    PayWayError(String code, String message) {
        initEnum(code, message);
    }
}
