package com.baosight.payment.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Data
public class MchChannelCorrelationVO {
    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 渠道id
     */
    private String channelId;


    /**
     * 渠道类型
     */
    private Integer type;

}
