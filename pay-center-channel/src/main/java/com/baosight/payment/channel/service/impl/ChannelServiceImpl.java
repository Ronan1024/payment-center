package com.baosight.payment.channel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.channel.dao.entity.Channel;
import com.baosight.payment.channel.service.ChannelService;
import com.baosight.payment.channel.dao.mapper.ChannelMapper;
import org.springframework.stereotype.Service;

/**
* @author longjiangran
* @description 针对表【channel(支付渠道表)】的数据库操作Service实现
* @createDate 2026-06-12 11:08:56
*/
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel>
    implements ChannelService{

}




