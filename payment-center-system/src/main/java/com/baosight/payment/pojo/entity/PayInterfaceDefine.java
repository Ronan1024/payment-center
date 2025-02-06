package com.baosight.payment.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baosight.database.base.BasePO;
import lombok.Data;

/**
 * 支付接口定义表
 *
 * @TableName pay_interface_define
 */
@Data
@TableName(value = "pay_interface_define")
public class PayInterfaceDefine extends BasePO implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 是否支持普通商户
     */
    private Boolean hasMch;

    /**
     * 是否支持服务商模式
     */
    private Boolean hasIsvMch;

    /**
     * 服务商支付参数配置
     */
    private String isvParams;

    /**
     * 特约商户配置
     */
    private String isvSubMchParams;

    /**
     * 普通商户支付参数配置
     */
    private String normalMchParams;

    /**
     * 备注
     */
    private String remark;

    /**
     *
     */
    private Long createBy;


    /**
     *
     */
    private Long updateBy;

    /**
     * 支付方式列表使用, 分割
     */
    private String payWay;
    /**
     * 是否开启
     */
    private Boolean enable;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}