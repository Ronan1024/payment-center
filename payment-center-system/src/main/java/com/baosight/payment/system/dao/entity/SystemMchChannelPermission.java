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
 * @TableName system_mch_channel_permission
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="system_mch_channel_permission")
public class SystemMchChannelPermission extends BasePO {
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
     * 商户id
     */
    private Long mchId;

    /**
     * 商户类型
     */
    private Integer mchType;
}