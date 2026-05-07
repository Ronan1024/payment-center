package com.baosight.payment.channel.enums;

/**
 * 统一渠道回调业务状态。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
public enum CallbackHandleStatus {

    /**
     * 明确成功。
     */
    SUCCESS,

    /**
     * 明确失败。
     */
    FAIL,

    /**
     * 渠道仍在处理中。
     */
    PROCESSING,

    /**
     * 无法确认结果。
     */
    UNKNOWN
}
