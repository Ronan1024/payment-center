package com.baosight.payment.check.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * 渠道账单状态
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/8
 */
public enum ChannelBillHandlerState implements IBaseEnum<Integer> {
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
    ChannelBillHandlerState(Integer code, String msg){
        initEnum(code, msg);
    }
}
