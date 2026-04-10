package com.baosight.payment.system.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务商通道权限
 * @author L.J.Ran
 * @TableName system_client_channel_permission
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="system_client_channel_permission")
public class SystemClientChannelPermission extends BasePO {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 通道编号
     */
    private String channelCode;

    /**
     * 通道配置定义id
     */
    private Long channelDefineId;

    /**
     * 客户端id
     */
    private Long clientId;

    /**
     * 客户端类型
     */
    private Integer clientType;
}