package com.baosight.payment.channel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.channel.pojo.entity.ChannelGatewayLog;
import org.apache.ibatis.annotations.Mapper;

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

}
