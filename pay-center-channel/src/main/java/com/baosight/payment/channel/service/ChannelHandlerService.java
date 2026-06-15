package com.baosight.payment.channel.service;

import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerListRespDTO;

import java.util.List;

/**
 * 渠道处理器管理服务。
 */
public interface ChannelHandlerService {

    /**
     * 查询渠道处理器列表。
     */
    List<ChannelHandlerListRespDTO> list();

    /**
     * 查询渠道处理器详情。
     *
     * @param handlerNo 处理器编号
     */
    ChannelHandlerInfoRespDTO info(String handlerNo);
}
