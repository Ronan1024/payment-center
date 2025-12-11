package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;


public enum PayOrderOption implements IBaseEnum<Integer> {
    /**
     * 生成订单
     */
    STATE_INIT(1, "生成订单"),
    ;
    PayOrderOption(Integer code, String msg){
        initEnum(code, msg);
    }
}
