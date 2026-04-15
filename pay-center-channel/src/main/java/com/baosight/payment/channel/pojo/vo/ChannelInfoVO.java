package com.baosight.payment.channel.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 支付渠道信息 VO
 *
 * @author L.J.Ran
 * @since 2026-03-04
 */
@Data
public class ChannelInfoVO {

    /**
     * 渠道ID
     */
    private Long id;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道机构
     */
    private String channelAgency;

    /**
     * 渠道类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
