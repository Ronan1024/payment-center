package com.baosight.payment.isv.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 服务商信息表
 *
 * @TableName pay_isv_info
 */
@Data
@TableName(value = "pay_isv_info")
@EqualsAndHashCode(callSuper = true)
public class PayIsvInfo extends BasePO implements Serializable {
    /**
     * 服务商号
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
    private String shortName;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者用户ID
     */
    private Long createBy;

    /**
     * 创建者姓名
     */
    private String createByName;

    private String updateBy;

    private String updateByName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}