package com.baosight.payment.system.error;

import com.baosight.utils.enums.IBaseEnum;

public enum TongLianError implements IBaseEnum<String> {
    /**
     * 当前手机号已申请绑定
     */
    TONG_LIAN_MOBILE_BIND_ERROR("50001", "当前手机号已申请绑定"),
    /**
     * 当前手机号未申请绑定
     */
    TONG_LIAN_MOBILE_UNBIND_ERROR("50002", "当前手机号未申请"),
    /**
     * 通联用户信息获取异常
     */
    TONG_LIAN_USER_INFO_ERROR("50003", "通联用户信息获取异常"),

    ;

    TongLianError(String code, String  msg){
        initEnum(code, msg);
    }
}
