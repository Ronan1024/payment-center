package com.baosight.payment.channel.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 *
 * 能力接口状态
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/11
 */
public enum InterfaceStatusEnum implements IBaseEnum<Integer> {

    /**
     * 停用
     */
    DISABLE(0, "停用"),

    /**
     * 启用
     */
    ENABLE(1, "启用"),

    /**
     * 维护中
     */
    MAINTAIN(2, "维护中"),

    /**
     * 下线
     */
    OFFLINE(3, "下线"),

    ;

    InterfaceStatusEnum(Integer code, String desc) {
        initEnum(code, desc);
    }
}
