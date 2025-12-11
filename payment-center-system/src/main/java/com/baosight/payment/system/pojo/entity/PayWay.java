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
 * 支付方式表
 *
 * @author L.J.Ran
 * @TableName pay_way
 */
@Data
@TableName(value = "pay_way")
@EqualsAndHashCode(callSuper = true)
public class PayWay extends BasePO implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 支付方式代码 例如：WX_PAY ALI_PAY
     */
    private String payCode;

    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 更新人名称
     */
    private String updateByName;
    /**
     * 支付机构
     */
    private String payingAgency;

    /**
     * 支付客户端
     */
    private Integer payingClient;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}