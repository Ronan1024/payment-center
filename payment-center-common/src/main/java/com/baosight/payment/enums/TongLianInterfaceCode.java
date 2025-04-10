package com.baosight.payment.enums;

import com.baosight.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/26
 */

public enum TongLianInterfaceCode implements IBaseEnum<String> {
    /**
     * 绑定收银宝
     */
    BIND_SYB("1024", "绑定收银宝"),

    /**
     * 绑定手机号申请
     */
     BIND_PHONE_REPORT("1030", "绑定手机号申请"),
    /**
     * 线上签约申请
     */
    ONLINE_PROTOCOL_SIGN_APPLY("1050", "线上签约申请")

    ;

    TongLianInterfaceCode(String code, String msg) {
        initEnum(code, msg);
    }
}
