package com.baosight.payment.channel.service;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.pojo.dto.req.ChannelGatewayLogPageReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelGatewayLogInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelGatewayLogPageRespDTO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/17
 */
public interface ChannelGatewayLogService {
    /**
     * 获取渠道网关日志记录分页列表
     * @param channelGatewayLogPage 渠道网关请求参数
     */
    PageResponse<ChannelGatewayLogPageRespDTO> page(ChannelGatewayLogPageReqDTO channelGatewayLogPage);

    /**
     * 获取渠道网关日志记录信息
     * @param id 渠道网关记录id
     */
    ChannelGatewayLogInfoRespDTO info(Long id);
}
