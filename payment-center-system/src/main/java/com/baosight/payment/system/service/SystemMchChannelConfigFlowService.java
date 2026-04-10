package com.baosight.payment.system.service;

import com.baosight.payment.channel.event.ChannelFlowExecuteResultListenerEvent;
import com.baosight.payment.system.dao.entity.SystemMchChannelConfigFlow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author longjiangran
 * @description 针对表【system_mch_channel_config_flow(渠道配置流程)】的数据库操作Service
 * @createDate 2026-03-27 13:53:50
 */
public interface SystemMchChannelConfigFlowService extends IService<SystemMchChannelConfigFlow> {


    /**
     * 执行
     */
    void execute(Long clientId, Integer clientType, String channelCode, String body);

    /**
     * 更新商户渠道配置流程执行结果
     *
     * @param event 渠道配置执行结果事件
     * @return
     */
    Boolean updateMchChannelConfigFlow(ChannelFlowExecuteResultListenerEvent event);
}
