package com.baosight.payment.channel.handler.channelflow;

import com.baosight.payment.api.PlatformConfigurationApi;
import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.pojo.dao.ChannelGatewayLogDAO;
import com.baosight.payment.dao.resp.SystemRespDTO;
import com.baosight.payment.enums.PayingAgency;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
public abstract class ChannelFlowAbstractHandler implements IChannelFlowOption {
    protected final PlatformConfigurationApi platformConfigurationApi;

    protected ChannelFlowAbstractHandler(PlatformConfigurationApi platformConfigurationApi) {
        this.platformConfigurationApi = platformConfigurationApi;
    }

    protected ChannelGatewayLogDAO channelGatewayLog(Long clientId, Integer clientType, ChannelGatewayLog.Operation operation, PayingAgency payingAgency) {
        ChannelGatewayLogDAO log = new ChannelGatewayLogDAO();
        log.setOperation(operation.getCode())
//                .setBizType(eventType().code())
//                .setBizTypeName(eventType().desc())
                .setClientType(clientType)
                .setClientId(clientId)
                .setInstCode(payingAgency.code())
                .setChannelCode(channelCode().code())
                .setBizStatus(ChannelGatewayLog.BizStatus.CREATE.getCode());
        return log;
    }


    /**
     * 获取系统配置信息
     *
     */
    protected SystemRespDTO systemConfig() {
        return platformConfigurationApi.systemConfig();
    }

}
