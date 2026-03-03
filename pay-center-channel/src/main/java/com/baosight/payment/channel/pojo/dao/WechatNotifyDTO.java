package com.baosight.payment.channel.pojo.dao;


import lombok.Data;

@Data
public class WechatNotifyDTO {
    /**
     * 通知ID
     */
    private String id;

    /**
     * 通知创建时间
     * 示例：2015-05-20T13:29:35+08:00
     */
    private String createTime;

    /**
     * 通知资源数据类型
     * 固定值：encrypt-resource
     */
    private String resourceType;

    /**
     * 通知类型
     * 示例：TRANSACTION.SUCCESS
     */
    private String eventType;

    /**
     * 回调摘要
     * 示例：支付成功
     */
    private String summary;

    /**
     * 加密的资源数据
     */
    private WechatResource resource;
}
