package com.baosight.payment.channel.handler;

import com.baosight.payment.channel.dao.manager.ChannelInterfaceManager;
import com.baosight.payment.channel.handler.capability.Capability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 渠道接口异步监听
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/12
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelInterfaceStartupSyncListener {


    private final ChannelInterfaceManager channelInterfaceManager;

    private final List<Capability> capabilities;





}
