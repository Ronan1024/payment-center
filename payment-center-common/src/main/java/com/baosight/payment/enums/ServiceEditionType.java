package com.baosight.payment.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * 服务版本类型
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/5
 */
public enum ServiceEditionType implements IBaseEnum<String> {
    ;

    ServiceEditionType(String code, String msg){
        initEnum(code, msg);
    }
}
