package com.baosight.payment.settlement.enums;

import com.baosight.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */

public enum AppleState implements IBaseEnum<Integer> {
    /**
     * 初始化
     */
    INIT(0, "初始化"),
    /**
     * 处理中
     */
    PROCESSING(1, "处理中"),
    /**
     * 处理成功
     */
    SUCCESS(2, "处理成功"),
    /**
     * 处理失败
     */
    FAILURE(3, "处理失败"),
    ;

    AppleState(Integer code, String msg){
        initEnum(code, msg);
    }
}
