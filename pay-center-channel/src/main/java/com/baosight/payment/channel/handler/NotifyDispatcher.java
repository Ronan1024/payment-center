package com.baosight.payment.channel.handler;


import com.baosight.payment.channel.service.PayNotifyHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 自动识别来源
 *
 * @deprecated 使用 {@link com.baosight.payment.channel.handler.notify.ChannelNotifyHandler}
 * 作为新的渠道回调入口。
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Deprecated
@Component
public class NotifyDispatcher {

    @Autowired
    private List<PayNotifyHandler> handlers;

    /**
     * 分发渠道回调到旧版处理器。
     *
     * @param body 原始请求体
     * @param request HTTP 请求
     * @return 渠道响应内容
     * @deprecated 使用 {@link com.baosight.payment.channel.handler.notify.ChannelNotifyHandler#handle(String, HttpServletRequest)}
     */
    @Deprecated
    public String dispatch(String body, HttpServletRequest request) {
        for (PayNotifyHandler handler : handlers) {
            if (handler.support(body, request)) {
                return handler.handle(body, request);
            }
        }
        return "unsupported";
    }
}
