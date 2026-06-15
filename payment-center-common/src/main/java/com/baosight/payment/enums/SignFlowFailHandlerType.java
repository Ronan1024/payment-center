package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 签约流程步骤失败处理类型。
 *
 * <p>
 * 用于描述签约流程步骤执行失败后，流程应立即阻断、允许重试，
 * 或转入人工确认状态。
 * </p>
 *
 * @author L.J.Ran
 */
public enum SignFlowFailHandlerType implements IBaseEnum<Integer> {

    /**
     * 阻断当前流程，不再自动执行后续步骤。
     */
    BLOCK(1, "阻断流程"),

    /**
     * 保留失败步骤并允许重新执行。
     */
    RETRY(2, "允许重试"),

    /**
     * 转入人工确认状态，等待运营人员处理。
     */
    WAIT_MANUAL(3, "等待人工确认"),
    ;

    SignFlowFailHandlerType(Integer code, String description) {
        initEnum(code, description);
    }
}
