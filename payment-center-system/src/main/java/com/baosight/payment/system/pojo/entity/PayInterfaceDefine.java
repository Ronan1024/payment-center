package com.baosight.payment.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.payment.enums.PayClientType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 支付接口定义表
 *
 * @author L.J.Ran
 * @TableName pay_interface_define
 */
@Data
@EqualsAndHashCode(callSuper = true)
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

    /**
     * 支付机构
     */
    private String payingAgency;

    /**
     * 支付接口代码
     */
    private String code;

    /**
     * 商户支付渠道用户信息
     */
    private String mchChannelUserKey;

    /**
     * 支付类型ID
     */
    private Long payInterfaceTypeId;

    /**
     * 支付类型ID
     */
    private Long payWayId;

    /**
     * 应用场景
     */
    private Integer scenario;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public String interfaceParam(PayClientType payClientType) {
        if (payClientType.equals(PayClientType.SUB_MERCHANT)) {
            return this.isvSubMchParams;
        } else if (payClientType.equals(PayClientType.SERVICE_PROVIDER)) {
            return this.isvParams;
        } else {
            return this.normalMchParams;
        }
    }
}