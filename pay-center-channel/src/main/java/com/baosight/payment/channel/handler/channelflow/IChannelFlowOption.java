package com.baosight.payment.channel.handler.channelflow;

import lombok.Data;

/**
 * 渠道流程选择器抽象接口
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
public interface IChannelFlowOption {

    /**
     * 渠道编号
     *
     * @return
     */
    String channelCode();

    /**
     * 执行类型
     */
    String stepType();

    /**
     * 执行名称
     */
    String name();

    /**
     * 执行
     */
    ExecuteResult execute(Long mchId, Integer mchType, String param);

    @Data
    class ExecuteResult {
        /**
         * 执行结果是否成功
         */
        private Boolean success;

        /**
         * 执行结果
         */
        private String result;

        /**
         * 执行错误信息
         */
        private String errorMsg;

    }
}
