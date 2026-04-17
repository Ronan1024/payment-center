package com.baosight.payment.channel.service.impl;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.convert.ChannelGatewayLogConvert;
import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.dao.manager.ChannelGatewayLogManager;
import com.baosight.payment.channel.pojo.dto.req.ChannelGatewayLogPageReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelGatewayLogInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelGatewayLogPageRespDTO;
import com.baosight.payment.channel.service.ChannelGatewayLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/17
 */
@Service
@RequiredArgsConstructor
public class ChannelGatewayLogServiceImpl implements ChannelGatewayLogService {

    private final ChannelGatewayLogManager channelGatewayLogManager;


    /**
     * 获取渠道网关日志记录分页列表
     *
     * @param channelGatewayLogPage 渠道网关请求参数
     */
    @Override
    public PageResponse<ChannelGatewayLogPageRespDTO> page(ChannelGatewayLogPageReqDTO channelGatewayLogPage) {
        return channelGatewayLogManager.page(channelGatewayLogPage);
    }

    /**
     * 获取渠道网关日志记录信息
     *
     * @param id 渠道网关记录id
     */
    @Override
    public ChannelGatewayLogInfoRespDTO info(Long id) {
        ChannelGatewayLog one = channelGatewayLogManager.lambdaQuery()
                .eq(ChannelGatewayLog::getId, id).one();

        return ChannelGatewayLogConvert.INSTANCE.toChannelGatewayLogInfoRespDTO(one);
    }
}
