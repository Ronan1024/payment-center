package com.baosight.payment.system.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/9
 */
public enum ChannelError implements IErrorEnum<String> {
    /**
     * 渠道信息不存在
     */
    CHANNEL_INFO_NOT_EXIT("700001","渠道信息不存在"),
    ;
    ChannelError(String code,String msg){
        initEnum(code, msg);
    }
}
