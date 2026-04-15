package com.baosight.payment.channel.dao.mapper;

import com.baosight.payment.channel.dao.entity.ChannelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【paying_channel_info(支付渠道信息)】的数据库操作Mapper
* @createDate 2026-03-04 14:00:52
* @Entity com.baosight.payment.channel.dao.entity.ChannelInfo
*/
@Mapper
public interface ChannelInfoMapper extends BaseMapper<ChannelInfo> {

}




