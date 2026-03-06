package com.baosight.payment.channel.error;

import com.ronan.common.enums.IBaseEnum;

/**
 * 通联异常枚举
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/5
 */
public enum AllInError implements IBaseEnum<String> {
    /**
     * 调用成功
     */
    REQUEST_SUCCESS("00000", "调用成功(接口调用成功) | 交易成功"),
    ;


    AllInError(String code, String message) {
        initEnum(code, message);
    }
}
