package com.baosight.payment.check.enums;

import com.baosight.utils.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/5
 */
public enum CheckBatchRecordState implements IBaseEnum<Integer> {
    /**
     * 待处理
     */
    PENDING(1, "待处理"),
    /**
     * 处理中
     */
    PROCESSING(2, "处理中"),
    /**
     * 对账成功
     */
    SUCCESS(3, "对账成功"),
    /**
     * 部分成功
     */
    PARTIAL_SUCCESS(4, "部分成功"),
    /**
     * 差异处理中
     */
    DIFFERENCES_PROCESSING(5, "差异处理中"),

    /**
     * 已关闭
     */
    CLOSED(6, "已关闭");
    ;

    CheckBatchRecordState(Integer code, String msg){
        initEnum(code, msg);
    }
}
