package com.baosight.payment.channel.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * 渠道处理器异常
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
public enum ChannelHandlerError implements IErrorEnum<String> {
    /**
     * 渠道处理器不存在
     */
    NOT_EXIST("A000020", "渠道处理器不存在"),
    ;

    ChannelHandlerError(String code, String message){
        initEnum(code, message);
    }
}
