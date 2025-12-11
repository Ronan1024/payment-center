package com.baosight.payment.settlement.error;


import com.baosight.web.core.exception.IErrorEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */
public enum AccountError implements IErrorEnum<String> {
    /**
     * 商户账号不存在
     */
    MCH_ACCOUNT_NOT_FOUND("10000", "商户账号不存在"),
    /**
     * 当前账户余额不足
     */
    ACCOUNT_BALANCE_INSUFFICIENT("10001", "当前账户余额不足"),
    /**
     * 当前商户正在提现处理中请稍后再试
     */
    MCH_WITHDRAWAL_PROCESSING("10002", "当前商户正在提现处理中请稍后再试"),
    /**
     * 提现记录未找到
     */
    WITHDRAWAL_RECORD_NOT_FOUND("10003", "提现记录未找到"),
    /**
     * 冻结金额不足
     */
    FREEZE_AMOUNT_INSUFFICIENT("10004", "冻结金额不足"),
    ;

    AccountError(String code, String msg){
        initEnum(code, msg);
    }
}
