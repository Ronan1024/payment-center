package com.baosight.payment.channel.pojo.dto.resp;

import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/17
 */
@Data
public class ChannelGatewayLogInfoRespDTO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 操作类型 1 出站(请求) 2 入站(回调/通知)
     */
    private Integer operation;

    /**
     * 业务id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 系统流水号/请求编号
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long requestNo;

    /**
     * 操作客户端类型
     */
    private String clientType;

    /**
     * 操作客户端编号
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long clientId;


    /**
     * 外部系统交易号
     */
    private String outTradeNo;

    /**
     * 支付机构编号
     */
    private String instCode;

    /**
     * 支付渠道编号
     */
    private String channelCode;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorMsg;

    /**
     * 耗时(毫秒)
     */
    private Integer costTime;

    /**
     * 出站参数
     */
    private String reqParams;

    /**
     * 入站参数
     */
    private String resParams;

    /**
     * 业务处理状态 1 创建  2 处理中 3 成功 4 失败
     */
    private Integer bizStatus;

    /**
     * 渠道接口id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long channelInterfaceId;


    /**
     * 出站url
     */
    private String reqUrl;
}
