package com.baosight.payment.error;

import com.baosight.web.core.exception.IErrorEnum;
import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 异步回调异常处理
 * @author: L.J.Ran
 * @create: 2025/3/19
 */

public enum NoticeError implements IErrorEnum<String> {

    /**
     * 参数校验失败
     */
    PARAMETER_CHECK_ERROR("30001", "支付机构: {} 参数校验失败"),
    ;

    NoticeError(String code, String msg){
        initEnum(code, msg);
    }
}
