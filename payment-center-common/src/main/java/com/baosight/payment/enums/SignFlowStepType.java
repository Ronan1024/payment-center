package com.baosight.payment.enums;

import com.ronan.common.enums.IBaseEnum;

/**
 * 签约流程步骤执行类型。
 *
 * <p>
 * 用于描述签约流程步骤由后端、前端或人工执行，以及步骤是否需要等待异步结果。
 * </p>
 *
 * @author L.J.Ran
 */
public enum SignFlowStepType implements IBaseEnum<Integer> {

    /**
     * 后端自动执行步骤。
     */
    BACKEND_AUTO(1, "后端自动执行"),

    /**
     * 由前端组件承载用户交互并完成步骤。
     */
    FRONTEND_COMPONENT(2, "前端组件"),

    /**
     * 打开独立页面或渠道授权页面完成步骤。
     */
    OPEN_PAGE(3, "打开页面"),

    /**
     * 等待渠道异步回调后继续流程。
     */
    WAIT_CALLBACK(4, "等待回调"),

    /**
     * 等待运营人员人工确认后继续流程。
     */
    MANUAL_CONFIRM(5, "等待人工确认"),
    ;

    SignFlowStepType(Integer code, String description) {
        initEnum(code, description);
    }
}
