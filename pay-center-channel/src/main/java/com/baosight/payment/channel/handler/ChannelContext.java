package com.baosight.payment.channel.handler;


import com.baosight.payment.channel.error.ChannelError;
import com.baosight.payment.channel.handler.channel.IChannel;
import com.baosight.web.core.exception.ServerException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
@Component
public class ChannelContext {
    private static final ConcurrentHashMap<String, IChannel> CHANNEL_CONTEXT = new ConcurrentHashMap<>();
    private final List<IChannel> channels;

    public ChannelContext(List<IChannel> channels) {
        this.channels = channels;
    }

    @PostConstruct
    public void init() {
        channels.forEach(e -> CHANNEL_CONTEXT.put(e.channelCode(), e));
    }


    /**
     * 装载渠道
     */
    public static void set(IChannel channel) {
        String code = channel.channelCode();
        if (!StringUtils.hasText(code)) {
            throw new ServerException(ChannelError.CHANNEL_CODE_ERROR.getMsg());
        }
        if (CHANNEL_CONTEXT.containsKey(code)) {
            throw new ServerException(ChannelError.CHANNEL_EXIST.getMsg());
        }
        CHANNEL_CONTEXT.put(code, channel);
    }


    /**
     * 渠道列表
     */
    public static Map<String, String> channelList() {
        return CHANNEL_CONTEXT.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().channelName()));
    }

    /**
     * 获取渠道信息
     *
     * @param channelCode
     * @return
     */
    public static IChannel getChannel(String channelCode) {
        return CHANNEL_CONTEXT.get(channelCode);
    }
}
