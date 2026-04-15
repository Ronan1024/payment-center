package com.baosight.payment.channel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付渠道信息
 *
 * @TableName paying_channel_info
 */
@Data
@TableName(value = "channel_info")
@EqualsAndHashCode(callSuper = true)
public class ChannelInfo extends BasePO {

    @TableId(type = IdType.ASSIGN_ID)
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
     * 渠道类型 1.支付
     */
    private Integer type;
}