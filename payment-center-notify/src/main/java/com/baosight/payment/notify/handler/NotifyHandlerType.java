package com.baosight.payment.notify.handler;

import com.baosight.payment.enums.NotifyType;
import com.ronan.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/1
 */
public enum NotifyHandlerType implements IBaseEnum<Integer> {
    /**
     * 支付
     */
    PAY(NotifyType.PAY_SUCCESS.code(), PayNotifyHandler.MARK),

    /**
     * 退款
     */
    REFUND(NotifyType.REFUND_SUCCESS.code(), RefundNotifyHandler.MARK),
    /**
     * 提现
     */
    WITHDRAW(NotifyType.WITHDRAW_SUCCESS.code(), WithdrawNotifyHandler.MARK)
    ;


    NotifyHandlerType(Integer code, String msg) {
        initEnum(code, msg);
    }
}

