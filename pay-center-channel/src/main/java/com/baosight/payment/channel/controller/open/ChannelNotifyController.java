package com.baosight.payment.channel.controller.open;


import com.baosight.payment.channel.service.ChannelNotifyService;
import com.baosight.web.core.advice.IgnoreHandlerResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 渠道回调入口控制器。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Slf4j
@RestController
@RequestMapping("/call/back")
@RequiredArgsConstructor
public class ChannelNotifyController {

    private final ChannelNotifyService channelNotifyService;

    /**
     * 接收支付渠道异步回调通知。
     *
     * @param body    原始请求体
     * @param request HTTP 请求
     * @param channel 渠道
     * @param event   事件类型
     * @return 渠道要求的响应内容
     */
    @IgnoreHandlerResponse
    @PostMapping("/notify/{channel}/{event}/{bizId}")
    public String notify(@RequestBody(required = false) String body, HttpServletRequest request, @PathVariable String channel, @PathVariable String event, @PathVariable Long bizId) {
        return channelNotifyService.handle(body, request, channel, event, bizId);
    }
}
