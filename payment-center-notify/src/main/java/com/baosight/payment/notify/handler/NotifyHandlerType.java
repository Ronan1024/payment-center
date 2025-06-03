package com.baosight.payment.notify.handler;

import com.baosight.payment.enums.NotifyType;
import com.baosight.utils.enums.IBaseEnum;

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
    PAY(NotifyType.PAY_SUCCESS.getCode(), PayNotifyHandler.MARK),

    /**
     * 退款
     */
    REFUND(NotifyType.REFUND_SUCCESS.getCode(), RefundNotifyHandler.MARK),
    /**
     * 提现
     */
    WITHDRAW(NotifyType.WITHDRAW_SUCCESS.getCode(), WithdrawNotifyHandler.MARK)
    ;


    NotifyHandlerType(Integer code, String msg) {
        initEnum(code, msg);
    }
}

