package com.baosight.payment.accounting.error;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/8
 */
public enum BillError implements IBaseEnum<String> {
    /**
     * 渠道文件或code不能同时为空
     */
    CHANNEL_FILE_OR_CODE_NULL("10001", "渠道文件或code不能同时为空"),
    /**
     * 渠道文件不存在
     */
    CHANNEL_FILE_NOT_FOUND("10002", "渠道文件不存在"),
    ;

    BillError(String code, String msg){
        initEnum(code, msg);
    }
}
