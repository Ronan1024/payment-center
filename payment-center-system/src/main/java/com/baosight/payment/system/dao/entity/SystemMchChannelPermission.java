package com.baosight.payment.system.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 服务商通道权限
 * @author L.J.Ran
 * @TableName system_mch_channel_permission
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="system_mch_channel_permission")
public class SystemMchChannelPermission extends BasePO {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 通道编号
     */
    private String channelCode;

    /**
     * 通达配置定义id
     */
    private Long channelDefineId;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 商户类型
     */
    private Integer mchType;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;
}