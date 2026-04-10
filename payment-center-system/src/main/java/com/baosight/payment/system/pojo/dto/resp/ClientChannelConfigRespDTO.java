package com.baosight.payment.system.pojo.dto.resp;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/9
 */
@Data
public class ClientChannelConfigRespDTO {
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
    private Map<String, Object> dynamicForm;

    /**
     * 渠道流程
     */
    private String flow;


    /**
     * 渠道流程列表
     */
    private List<MchChannelFlowRespDTO> channelFlowList;
}
