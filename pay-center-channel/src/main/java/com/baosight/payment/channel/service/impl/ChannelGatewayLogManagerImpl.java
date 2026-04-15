//package com.baosight.payment.channel.service.impl;
//
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baosight.payment.channel.dao.entity.ChannelGatewayLog;
//import com.baosight.payment.channel.dao.mapper.ChannelGatewayLogMapper;
//import com.baosight.payment.channel.service.ChannelGatewayLogManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
///**
// * <p>
// * 渠道网关出入站日志 服务实现类
// * </p>
// *
// * @author zhuzhuangzhi
// * @since 2026-01-29
// */
//@Service
//@RequiredArgsConstructor
//public class ChannelGatewayLogManagerImpl extends ServiceImpl<ChannelGatewayLogMapper, ChannelGatewayLog> implements ChannelGatewayLogManager {
//
//    private final  ChannelGatewayLogMapper channelGatewayLogMapper;
//
//
//    /**
//     * 新增渠道入站&&出战日志
//     * @param channelGatewayLog
//     * @return
//     */
//    @Override
//    public Boolean saveChannelGatewayLog(ChannelGatewayLog channelGatewayLog) {
//        return channelGatewayLogMapper.insert(channelGatewayLog) > 0;
//    }
//
//
//    /**
//     * 修改渠道日志状态
//     * @param channelGatewayLog
//     * @return
//     */
//    @Override
//    public Boolean updateChannelGatewayLog(ChannelGatewayLog channelGatewayLog) {
//        ChannelGatewayLog channelGatewayLogInfo = channelGatewayLogMapper.selectById(channelGatewayLog.getId());
//        Assert.isNull(channelGatewayLogInfo,"渠道日志信息不存在");
//        channelGatewayLogInfo.setBizStatus(channelGatewayLog.getBizStatus());
//        channelGatewayLogInfo.setCostTime(channelGatewayLog.getCostTime());
//        channelGatewayLogInfo.setErrorCode(channelGatewayLog.getErrorCode());
//        channelGatewayLogInfo.setErrorMsg(channelGatewayLog.getErrorMsg());
//        return channelGatewayLogMapper.updateById(channelGatewayLogInfo) > 0;
//    }
//}
