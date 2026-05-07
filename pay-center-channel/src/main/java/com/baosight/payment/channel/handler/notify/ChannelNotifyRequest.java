package com.baosight.payment.channel.handler.notify;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 渠道回调入站请求。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Data
@Accessors(chain = true)
public class ChannelNotifyRequest {

    /**
     * 原始请求体。
     */
    private String body;

    /**
     * HTTP 请求方法。
     */
    private String method;

    /**
     * 请求地址。
     */
    private String requestUri;

    /**
     * 请求头，key 统一为小写。
     */
    private Map<String, String> headers;

    /**
     * 请求参数。
     */
    private Map<String, String> params;
}
