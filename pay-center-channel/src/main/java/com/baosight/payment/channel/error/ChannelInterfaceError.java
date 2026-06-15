package com.baosight.payment.channel.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/12
 */
public enum ChannelInterfaceError implements IErrorEnum<String> {
    /**
     * 原因不能为空
     */
    REMARK_NULL_ERROR("A000030", "原因不能为空"),
    ;
    ChannelInterfaceError(String code, String message){
        initEnum(code, message);
    }
}
