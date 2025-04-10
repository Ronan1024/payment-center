package com.baosight.payment.enums;

import com.baosight.common.enums.IBaseEnum;

/**
 * @program: payment-center
 * @description: 支付渠道状态枚举
 * @author: L.J.Ran
 * @create: 2025/3/27
 */

public enum ChannelState implements IBaseEnum<Integer> {
    /**
     * 接口正确返回： 业务状态已经明确成功
     */
    SUCCESS(1, "业务状态已经明确成功"),
    /**
     * 接口正确返回： 业务状态已经明确失败
     */
    FAIL(2, "业务状态已经明确失败"),
    /**
     * 接口正确返回： 上游处理中， 需通过定时查询/回调进行下一步处理
     */
    PROCESSING(3, "上游处理中， 需通过定时查询/回调进行下一步处理"),
    /**
     * 接口超时，或网络异常等请求， 或者返回结果的签名失败： 状态不明确 ( 上游接口变更, 暂时无法确定状态值 )
     */
    UNKNOWN(4, "接口超时，或网络异常等请求， 或者返回结果的签名失败： 状态不明确 ( 上游接口变更, 暂时无法确定状态值 )"),
    /**
     * 渠道侧出现异常( 接口返回了异常状态 )
     */
    CHANNEL_ERROR(5, "渠道侧出现异常( 接口返回了异常状态 )"),
    /**
     * 本系统出现不可预知的异常
     */
    SYSTEM_ERROR(6, "本系统出现不可预知的异常");


    ChannelState(Integer code, String msg) {
        initEnum(code, msg);
    }
}
