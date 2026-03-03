package com.baosight.payment.channel.handler;


import com.baosight.payment.channel.service.PayNotifyHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 自动识别来源
 */
@Component
public class NotifyDispatcher {

    @Autowired
    private List<PayNotifyHandler> handlers;

    public String dispatch(String body, HttpServletRequest request) {
        for (PayNotifyHandler handler : handlers) {
            if (handler.support(body, request)) {
                return handler.handle(body, request);
            }
        }
        return "unsupported";
    }
}
