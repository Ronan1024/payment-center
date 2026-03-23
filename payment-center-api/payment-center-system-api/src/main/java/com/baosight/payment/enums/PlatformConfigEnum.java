package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 平台系统配置
 * @author: L.J.Ran
 * @create: 2026/3/10
 */
public enum PlatformConfigEnum implements IBaseEnum<String> {
    /**
     * 通联
     */
    ALL_IN("AllIn", "通联")



    ;

    PlatformConfigEnum(String code, String msg){
        initEnum(code, msg);
    }
}
