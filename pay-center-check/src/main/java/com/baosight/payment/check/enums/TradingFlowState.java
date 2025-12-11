package com.baosight.payment.check.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 交易流水装状态
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/15
 */

public enum TradingFlowState implements IBaseEnum<Integer> {
    /**
     * 待处理
     */
    PENDING(1, "待处理"),
    /**
     * 处理中
     */
    PROCESSING(2, "处理中"),

    /**
     * 处理完成
     */
    COMPLETED(3, "处理完成"),
    ;

    TradingFlowState(Integer code, String msg){
        initEnum(code, msg);
    }
}
