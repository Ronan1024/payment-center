package com.baosight.payment.channel.pojo.dto.req;

import com.baosight.database.core.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelGatewayLogPageReqDTO extends PageRequest {

    /**
     * 操作类型 1 出站(请求) 2 入站(回调/通知)
     */
    private Integer operation;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 操作客户端类型
     */
    private String clientType;

    /**
     * 操作客户端编号
     */
    private Long clientId;


    /**
     * 支付机构编号
     */
    private String instCode;

    /**
     * 支付渠道编号
     */
    private String channelCode;

    /**
     * 业务处理状态 1 创建  2 处理中 3 成功 4 失败
     */
    private Integer bizStatus;

}
