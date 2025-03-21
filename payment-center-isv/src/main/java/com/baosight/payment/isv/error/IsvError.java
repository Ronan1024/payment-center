package com.baosight.payment.isv.error;

import com.baosight.utils.enums.IBaseEnum;


public enum IsvError  implements IBaseEnum<String> {
    /**
     * 服务商数据异常
     */
    ISV_DATA_ERROR("10201", "服务商数据异常")
    ;

    IsvError(String code, String msg){
        initEnum(code,msg);
    }
}
