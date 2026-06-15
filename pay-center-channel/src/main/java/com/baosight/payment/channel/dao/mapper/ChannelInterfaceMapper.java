package com.baosight.payment.channel.dao.mapper;

import com.baosight.payment.channel.dao.entity.ChannelInterface;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfacePageReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfacePageRespDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 渠道接口 Mapper。
 *
 * <p>
 * 负责渠道接口能力表的基础 CRUD 和运营端分页查询。分页查询直接返回轻量列表响应对象，
 * 避免 Service 层再做不必要的实体到列表 DTO 转换。
 * </p>
 *
 * @author longjiangran
 * @description 针对表【channel_interface(渠道接口能力表)】的数据库操作Mapper
 * @createDate 2026-06-12 09:26:54
 * @Entity com.baosight.payment.channel.dao.entity.ChannelInterface
 */
@Mapper
public interface ChannelInterfaceMapper extends BaseMapper<ChannelInterface> {

    /**
     * 分页查询渠道接口。
     */
    IPage<ChannelInterfacePageRespDTO> page(@Param("page") Page<ChannelInterfacePageRespDTO> page,
                                            @Param("req") ChannelInterfacePageReqDTO req);
}




