package com.baosight.payment.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 商户应用表
 *
 * @author L.J.Ran
 * @TableName pay_mch_app
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pay_mch_app")
public class PayMchApp extends BasePO implements Serializable {
    /**
     * 应用ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 商户号
     */
    private Long mchId;

    /**
     * 应用状态: 0-停用, 1-正常
     */
    private Integer state;

    /**
     * 应用私钥
     */
    private String appSecret;

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
    private String createdByName;

    /**
     * 应用编号
     */
    private String appCode;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}