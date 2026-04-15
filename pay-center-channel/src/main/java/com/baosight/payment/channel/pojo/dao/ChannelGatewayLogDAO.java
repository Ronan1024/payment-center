package com.baosight.payment.channel.pojo.dao;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/15
 */
@Data
@Accessors(chain = true)
public class ChannelGatewayLogDAO {

    /**
     * 操作类型 1 出站(请求) 2 入站(回调/通知)
     */
    private Integer operation;

    /**
     * 系统流水号/请求编号
     */
    private Long requestNo;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务类型名称
     */
    private String bizTypeName;

    /**
     * 操作客户端类型
     */
    private Integer clientType;

    /**
     * 操作客户端编号
     */
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
    private Long costTime;

    /**
     * 出站参数
     */
    private String reqParams;

    /**
     * 出站url
     */
    private String reqUrl;


    /**
     * 入站参数
     */
    private String resParams;

    /**
     * 入站返回码
     */
    private String resCode;

    /**
     * 业务处理状态 1 创建  2 处理中 3 成功 4 失败
     */
    private Integer bizStatus;

    /**
     * 渠道接口id
     */
    private Long channelInterfaceId;

}
