package com.baosight.payment.channel.dao.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.database.core.annotation.Manager;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.payment.channel.dao.entity.ChannelInterface;
import com.baosight.payment.channel.dao.mapper.ChannelInterfaceMapper;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfacePageReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfacePageRespDTO;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 渠道接口数据管理器。
 *
 * <p>
 * 负责封装渠道接口表的持久化访问细节，向 Service 层提供分页查询和基础列表查询能力。
 * </p>
 *
 * @program: payment-center
 * @description: 渠道接口数据访问封装
 * @author: L.J.Ran
 * @create: 2026/6/12
 */
@Manager
@RequiredArgsConstructor
public class ChannelInterfaceManager extends BaseManagerImpl<ChannelInterfaceMapper, ChannelInterface> {

    private final ChannelInterfaceMapper channelInterfaceMapper;

    /**
     * 分页查询渠道接口。
     */
    public PageResponse<ChannelInterfacePageRespDTO> page(ChannelInterfacePageReqDTO req) {
        PageUtil<ChannelInterfacePageRespDTO> pageUtil = new PageUtil<>(req);
        return pageUtil.builder(channelInterfaceMapper.page(pageUtil.Page(), req)).build();
    }


    /**
     * 获取所有渠道接口。
     */
    public List<ChannelInterface> channelInterfaceList(){
       return channelInterfaceMapper.selectList(new LambdaQueryWrapper<>());
    }



}
