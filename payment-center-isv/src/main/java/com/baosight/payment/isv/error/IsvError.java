package com.baosight.payment.isv.error;

import com.baosight.web.core.exception.IErrorEnum;


public enum IsvError  implements IErrorEnum<String> {
    /**
     * 服务商数据异常
     */
    ISV_DATA_ERROR("10201", "服务商数据异常")
    ;

    IsvError(String code, String msg){
        initEnum(code,msg);
    }
}
