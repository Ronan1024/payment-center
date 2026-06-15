package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/15
 */
@Getter
public enum ChannelConfigType implements IBaseEnum<Integer> {
    /**
     * 签约表单
     */
    SIGN_FORM(1, "签约表单"),

    /**
     * 签约流程
     */
    SIGN_FLOW(2, "签约流程"),
    /**
     * 进件流程
     */
    ENTRY_FLOW(3, "进件流程"),
    ;


    ChannelConfigType(Integer code, String message) {
        initEnum(code, message);
    }
}
