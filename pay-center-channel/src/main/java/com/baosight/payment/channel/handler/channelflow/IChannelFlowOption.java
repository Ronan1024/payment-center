package com.baosight.payment.channel.handler.channelflow;

import com.baosight.payment.channel.handler.ChannelHandler;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 渠道流程选择器抽象接口
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
public interface IChannelFlowOption extends ChannelHandler {

    /**
     * 渠道流程事件执行
     *
     * @param clientId   客户端ID
     * @param clientType 客户端类型
     * @param param      事件执行参数
     */
    ExecuteResult execute(Long clientId, Integer clientType, String param);

    @Data
    @Accessors(chain = true)
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

  