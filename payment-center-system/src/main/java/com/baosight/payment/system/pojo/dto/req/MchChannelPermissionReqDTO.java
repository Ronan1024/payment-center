package com.baosight.payment.system.pojo.dto.req;

import lombok.Data;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/6
 */
@Data
public class MchChannelPermissionReqDTO {

    /**
     * 商户id
     */
    private Long clientId;

    /**
     * 商户类型
     */
    private Integer clientType;


    /**
     * 渠道id列表
     */
    private List<Long> channelId;
}
