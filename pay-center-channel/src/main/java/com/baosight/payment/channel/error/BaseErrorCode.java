package com.baosight.payment.channel.error;

import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
public enum BaseErrorCode implements IErrorEnum<String> {
    /**
     * 系统执行异常 一级错误码
     */
    SYSTEM_EXECUTES("A000001", "系统执行异常"),
    /**
     * 请求数据不合法
     */
    DATA_EXCEPTION("A000002", "请求数据不合法")
    ;


    BaseErrorCode(String code,String msg){
        initEnum(code,msg);

    }
}
