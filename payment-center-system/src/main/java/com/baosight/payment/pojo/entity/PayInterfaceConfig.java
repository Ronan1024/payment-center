package com.baosight.payment.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 支付接口配置
 *
 * @TableName pay_interface_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pay_interface_config")
public class PayInterfaceConfig extends BasePO implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 客户端类型
     */
    private Integer clientType;

    /**
     * 客户端id 如服务商、商家等id
     */
    private Long clientId;

    /**
     * 支付接口id
     */
    private Long interfaceId;

    /**
     * 支付接口参数
     */
    private String interfaceParams;

    /**
     * 支付接口费率
     */
    private Long interfaceRate;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 备注信息
     */
    private String remark;

    private Long createBy;

    private Long updateBy;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}