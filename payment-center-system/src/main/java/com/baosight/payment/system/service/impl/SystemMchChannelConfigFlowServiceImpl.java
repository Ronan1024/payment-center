package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.channel.event.ChannelFlowExecuteListenerEvent;
import com.baosight.payment.channel.event.ChannelFlowExecuteResultListenerEvent;
import com.baosight.payment.system.dao.entity.SystemChannelFlowDefine;
import com.baosight.payment.system.dao.entity.SystemMchChannelConfigFlow;
import com.baosight.payment.system.dao.manager.SystemChannelFlowDefineManager;
import com.baosight.payment.system.dao.manager.SystemMchChannelConfigFlowManager;
import com.baosight.payment.system.dao.mapper.SystemMchChannelConfigFlowMapper;
import com.baosight.payment.system.service.SystemMchChannelConfigFlowService;
import com.baosight.utils.json.JsonUtil;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.baosight.payment.system.error.PaymentChannelFlowError.MERCHANT_CHANNEL_FLOW_PROCESSING;

/**
 * @author longjiangran
 * @description 针对表【system_mch_channel_config_flow(渠道配置流程)】的数据库操作Service实现
 * @createDate 2026-03-27 13:53:50
 */
@Service
@RequiredArgsConstructor
public class SystemMchChannelConfigFlowServiceImpl extends ServiceImpl<SystemMchChannelConfigFlowMapper, SystemMchChannelConfigFlow> implements SystemMchChannelConfigFlowService {

    private final SystemMchChannelConfigFlowManager systemMchChannelConfigFlowManager;

    private final SystemChannelFlowDefineManager systemChannelFlowDefineManager;

    @Resource
    private ApplicationEventPublisher publisher;


    /**
     * 处理并调用渠道流程执行器
     *
     * @param clientId   客户端ID
     * @param clientType 客户端类型
     * @param channelCode  渠道code
     * @param body       请求体
     */
    @Override
    public void execute(Long clientId, Integer clientType, String channelCode, String body) {
        List<SystemChannelFlowDefine> systemChannelFlowDefineList = systemChannelFlowDefineManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getChannelCode, channelCode)
                .eq(SystemChannelFlowDefine::getClientType, clientType)
                .list();
        if (systemChannelFlowDefineList.isEmpty()) {
            return;
        }
        SystemMchChannelConfigFlow mchChannelConfigFlow = systemMchChannelConfigFlowManager.lambdaQuery()
                .eq(SystemMchChannelConfigFlow::getChannelCode, channelCode)
                .eq(SystemMchChannelConfigFlow::getClientId, clientId).one();
        Map<Integer, SystemChannelFlowDefine> channelFlowMap = systemChannelFlowDefineList.stream().collect(Collectors.toMap(SystemChannelFlowDefine::getStepOrder, e -> e));
        int index = 1;
        if (!ObjectUtils.isEmpty(mchChannelConfigFlow.getIndex()) && mchChannelConfigFlow.getStatus().equals(SystemMchChannelConfigFlow.Status.FAILED.code())) {
            index = mchChannelConfigFlow.getIndex();
        }
        SystemChannelFlowDefine channelFlowDefine = channelFlowMap.get(index);
        if (!StringUtils.hasText(body) && StringUtils.hasText(channelFlowDefine.getUserInput())) {
            // body 为空表示可能未内部调用，但当前节点需要需要用户输入所以直接抛出
            return;
        }
        Assert.isTrue(mchChannelConfigFlow.getStatus().equals(SystemMchChannelConfigFlow.Status.PROCESSING.code()), ApiException.supplier(MERCHANT_CHANNEL_FLOW_PROCESSING));
        ChannelFlowExecuteListenerEvent listenerEvent = new ChannelFlowExecuteListenerEvent();
        listenerEvent.setChannelCode(mchChannelConfigFlow.getChannelCode())
                .setChannelId(mchChannelConfigFlow.getChannelId())
                .setClientId(clientId)
                .setChannelFlowType(channelFlowDefine.getStepType())
                .setClientType(clientType)
                .setBody(body);
        publisher.publishEvent(listenerEvent);
        mchChannelConfigFlow.setIndex(index);
        mchChannelConfigFlow.setStatus(SystemMchChannelConfigFlow.Status.PROCESSING.code());
        systemMchChannelConfigFlowManager.updateById(mchChannelConfigFlow);

    }

    /**
     * 更新商户渠道配置流程执行结果
     *
     * @param event 渠道配置执行结果事件
     * @return
     */
    @Override
    public Boolean updateMchChannelConfigFlow(ChannelFlowExecuteResultListenerEvent event) {
        SystemMchChannelConfigFlow mchChannelConfigFlow = systemMchChannelConfigFlowManager.lambdaQuery()
                .eq(SystemMchChannelConfigFlow::getChannelCode, event.getChannelCode())
                .eq(SystemMchChannelConfigFlow::getClientId, event.getClientId())
                .eq(SystemMchChannelConfigFlow::getClientType, event.getClientType())
                .eq(SystemMchChannelConfigFlow::getStatus, SystemMchChannelConfigFlow.Status.PROCESSING.code()).one();

        SystemChannelFlowDefine channelFlowDefine = systemChannelFlowDefineManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getChannelCode, event.getChannelCode())
                .eq(SystemChannelFlowDefine::getStepType, event.getChannelFlowType()).one();


        if (ObjectUtils.isEmpty(mchChannelConfigFlow)) {
            return Boolean.FALSE;
        }
        Integer status = Boolean.TRUE.equals(event.getSuccess()) ? SystemMchChannelConfigFlow.Status.SUCCESS.code() : SystemMchChannelConfigFlow.Status.FAILED.code();
        mchChannelConfigFlow.setStatus(status);
        String wholeFlow = mchChannelConfigFlow.getWholeFlow();
        List<SystemMchChannelConfigFlow.WholeFlow> wholeFlows = new ArrayList<>();
        if (StringUtils.hasText(wholeFlow)) {
            wholeFlows = JsonUtil.parseArray(wholeFlow, SystemMchChannelConfigFlow.WholeFlow.class);
        }
        List<Long> list = wholeFlows.stream().map(SystemMchChannelConfigFlow.WholeFlow::getId).toList();
        if (list.contains(channelFlowDefine.getId())) {
            SystemMchChannelConfigFlow.WholeFlow flow = wholeFlows.stream().filter(e -> e.getId().equals(channelFlowDefine.getId())).findFirst().orElse(null);
            assert flow != null;
            flow.setStatus(status);
            flow.setHandleTime(event.getHandleTime());
            flow.setHandleResult(event.getExecuteResult());
            flow.setErrorMsg(event.getExceptionMsg());

        } else {
            SystemMchChannelConfigFlow.WholeFlow flow = new SystemMchChannelConfigFlow.WholeFlow();
            flow.setStatus(status);
            flow.setId(channelFlowDefine.getId());
            flow.setHandleTime(event.getHandleTime());
            flow.setStepType(event.getChannelFlowType());
            flow.setHandleResult(event.getExecuteResult());
            flow.setIndex(channelFlowDefine.getStepOrder());
            flow.setErrorMsg(event.getExceptionMsg());
            wholeFlows.add(flow);
        }
        mchChannelConfigFlow.setWholeFlow(JsonUtil.toJson(wholeFlows));
        systemMchChannelConfigFlowManager.updateById(mchChannelConfigFlow);
        if (Boolean.TRUE.equals(event.getSuccess())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}




