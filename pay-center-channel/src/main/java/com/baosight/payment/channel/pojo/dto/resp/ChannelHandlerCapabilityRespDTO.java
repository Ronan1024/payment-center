package com.baosight.payment.channel.pojo.dto.resp;

import lombok.Data;

/**
 * 渠道处理器能力响应。
 */
@Data
public class ChannelHandlerCapabilityRespDTO {

    /**
     * key
     */
    private String key;

    /**
     * 能力编号
     */
    private String code;

    /**
     * 能力组名称
     */
    private String group;

    /**
     * 场景名称
     */
    private String scene;

    /**
     * 行为
     */
    private String action;

    /**
     * 能力
     */
    private String name;

    /**
     * 支付品牌
     */
    private String payBrand;

    /**
     * 状态
     */
    private Integer staus;
}
