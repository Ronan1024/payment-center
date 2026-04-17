package com.baosight.payment.channel.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
import com.baosight.payment.channel.pojo.dto.req.ChannelGatewayLogPageReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelGatewayLogPageRespDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 渠道网关出入站日志 Mapper 接口
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2026-01-29
 */
@Mapper
public interface ChannelGatewayLogMapper extends BaseMapper<ChannelGatewayLog> {

    IPage<ChannelGatewayLogPageRespDTO> page(@Param("page") Page<ChannelGatewayLogPageRespDTO> page, @Param("req") ChannelGatewayLogPageReqDTO channelGatewayLogPageReqDTO);
}
