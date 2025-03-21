package com.baosight.payment.mch.error;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 商户异常
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
public enum MchError implements IBaseEnum<String> {
    /**
     * 服务商信息不能为空
     */
    ISV_INFO_IS_NULL("10001", "服务商信息不能为空"),
    /**
     * 商户信息不存在
     */
    MCH_INFO_NOT_FOUND("10002", "商户信息不存在"),
    /**
     * 当前商户信息已存在
     */
    MCH_INFO_EXIST("10003", "当前商户信息已存在"),
    ;

    MchError(String code, String msg) {
        initEnum(code, msg);
    }
}
