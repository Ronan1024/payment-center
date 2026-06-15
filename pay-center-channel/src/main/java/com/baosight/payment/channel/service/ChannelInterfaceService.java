package com.baosight.payment.channel.service;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfacePageReqDTO;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfaceStatusUpdateReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfaceInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfacePageRespDTO;

/**
 * 渠道接口管理服务。
 *
 * <p>
 * 封装运营端对渠道接口能力的分页查询、详情查看和状态维护操作。
 * 渠道接口用于连接渠道处理器、能力域、动作、支付品牌和支付场景。
 * </p>
 *
 * @author longjiangran
 * @description 针对表【channel_interface(渠道接口能力表)】的数据库操作Service
 * @createDate 2026-06-12 09:26:54
 */
public interface ChannelInterfaceService {

    /**
     * 分页查询渠道接口。
     */
    PageResponse<ChannelInterfacePageRespDTO> page(ChannelInterfacePageReqDTO req);

    /**
     * 查看渠道接口详情。
     */
    ChannelInterfaceInfoRespDTO info(Long id);

    /**
     * 修改渠道接口状态。
     */
    Boolean updateStatus(ChannelInterfaceStatusUpdateReqDTO req);
}
