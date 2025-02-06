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

/**
 * 支付服务商信息表
 *
 * @TableName pay_isv_info
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pay_isv_info")
public class PayIsvInfo extends BasePO implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 服务商名称
     */
    private String name;

    /**
     * 服务商简称
     */
    private Integer shortName;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createBy;


    /**
     * 更新人
     */
    private Long updateBy;


    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}