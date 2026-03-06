package com.baosight.payment.channel.error;


import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
public enum ChannelError implements IErrorEnum<String> {
    /**
     * 渠道编号异常无法进行装配
     */
    CHANNEL_CODE_ERROR("A000010", "渠道编号异常"),
    /**
     * 当前渠道已存在
     */
    CHANNEL_EXIST("A000011", "当前渠道已存在"),
    /**
     * 当前渠道编号已绑定
     */
    CHANNEL_CODE_BOUND_ALREADY("A000012", "渠道编号已绑定"),
    /**
     * 渠道信息不存在
     */
    CHANNEL_INFO_NOT_EXIST ("A000013", "渠道信息不存在"),

    ;


    ChannelError(String code, String message) {
        initEnum(code, message);
    }
}
