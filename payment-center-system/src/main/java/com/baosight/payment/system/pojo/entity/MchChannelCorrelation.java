package com.baosight.payment.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商户关联三方渠道信息
 * @author L.J.Ran
 * @TableName mch_channel_correlation
 */
@Data
@TableName(value ="mch_channel_correlation")
public class MchChannelCorrelation {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    private Long mchId;

    /**
     * 
     */
    private String mchNo;

    /**
     * 
     */
    private Integer type;

    /**
     * 渠道id
     */
    private String channelId;
}