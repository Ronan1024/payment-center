package com.baosight.payment.channel.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.channel.dao.entity.Channel;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【channel(支付渠道表)】的数据库操作Mapper
* @createDate 2026-06-12 11:08:56
* @Entity com.baosight.payment.channel.dao.entity.Channel
*/
@Mapper
public interface ChannelMapper extends BaseMapper<Channel> {

}




