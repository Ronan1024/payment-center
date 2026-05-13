package com.baosight.payment.channel.handler.channelflow;

import com.baosight.payment.channel.event.ChannelFlowExecuteResultListenerEvent;
import com.ronan.common.utils.Assert;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Component
public class ChannelFlowHandler {

    @Resource
    private ApplicationEventPublisher publisher;


    private static final ConcurrentHashMap<String, IChannelFlowOption> CONTEXT = new ConcurrentHashMap<>();

    public ChannelFlowHandler(List<IChannelFlowOption> channelFlowOption) {
        for (IChannelFlowOption option : channelFlowOption) {
            String key = option.channelCode().code() + "-" + option.eventType().code();
            Assert.isTrue(CONTEXT.containsKey(key), "渠道流程装载失败【" + option.eventType().code() + "】已存在");
            CONTEXT.put(key, option);
        }
    }


    /**
     * 获取渠道下可执行的流程列表
     *
     * @param channelCode 渠道编号
     */
    public Map<String, String> executeList(String channelCode) {
        return CONTEXT.values().stream()
                .filter(e -> e.channelCode().code().equals(channelCode))
                .collect(Collectors.toMap(e -> e.eventType().code(), e -> e.eventType().desc()));
    }

    /**
     * 获取渠道流程执行器
     *
     * @param channelCode 渠道编号
     * @param stepType    执行器类型
     */
    public IChannelFlowOption getChannelFlowOption(String channelCode, String stepType) {
        return CONTEXT.get(channelCode + "-" + stepType);
    }

    /**
     * 执行渠道流程
     *
     * @param channelCode 渠道code
     * @param stepType    执行器
     * @param clientId    商户id
     * @param clientType  商户类型
     * @param body        body
     */
    public void execute(String channelCode, String stepType, Long clientId, Integer clientType, String body) {
        IChannelFlowOption.ExecuteResult execute = getChannelFlowOption(channelCode, stepType)
                .execute(clientId, clientType, body);
        // 通知已执行成功
        ChannelFlowExecuteResultListenerEvent daoInstance = new ChannelFlowExecuteResultListenerEvent();
        daoInstance.setChannelCode(channelCode);
        daoInstance.setClientId(clientId);
        daoInstance.setClientType(clientType);
        daoInstance.setChannelFlowType(stepType);
        daoInstance.setExecuteResult(execute.getResult());
        daoInstance.setSuccess(execute.getSuccess());
        daoInstance.setExceptionMsg(execute.getErrorMsg());
        daoInstance.setHandleTime(new Date());
        publisher.publishEvent(daoInstance);
    }


}
