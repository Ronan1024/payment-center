package com.baosight.payment.system.pojo.dto.req;

import lombok.Data;

import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/9
 */
@Data
public class ClientChannelConfigReqDTO {
    /**
     * 支付渠道id
     */
    private Long channelId;

    /**
     * 客户端id
     */
    private Long clientId;

    /**
     * 客户端类型
     */
    private Integer clientType;
    /**
     * 是否启动
     */
    private Boolean enable;
    /**
     * 服务商通道费率
     */
    private Long isvRate;

    /**
     * 商户支付配置信息
     */
    private Map<String, String> dynamicForm;
}
