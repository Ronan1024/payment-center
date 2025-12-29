package com.baosight.payment.isv.error;

import com.baosight.web.core.exception.IErrorEnum;


public enum IsvError  implements IErrorEnum<String> {
    /**
     * 服务商数据异常
     */
    ISV_DATA_ERROR("10201", "服务商数据异常"),
    ISV_INFO_EXIST("10202", "服务商已存在")
    ;

    IsvError(String code, String msg){
        initEnum(code,msg);
    }
}
