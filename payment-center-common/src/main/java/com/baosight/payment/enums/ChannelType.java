package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */


public enum ChannelType implements IBaseEnum<Integer> {
    /**
     * 微信
     */
    WECHAT(1, "微信"),
    ;

    ChannelType(Integer code, String msg) {
        initEnum(code, msg);
    }
}
