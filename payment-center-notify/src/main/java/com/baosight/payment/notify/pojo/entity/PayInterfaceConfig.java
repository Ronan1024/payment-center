package com.baosight.payment.notify.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 支付接口配置
 * @TableName pay_interface_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="pay_interface_config")
public class PayInterfaceConfig extends BasePO {
    /**
     * 
     */
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
    private Object interfaceParams;

    /**
     * 支付接口费率  * 100
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

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Long createBy;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private Long updateBy;

    /**
     * 支付机构
     */
    private String payingAgency;

    /**
     * 支付名称
     */
    private String name;

    /**
     * 支付接口
     */
    private String payWay;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 支付渠道用户信息
     */
    private String mchChannelUser;

    /**
     * 接口编码
     */
    private String interfaceCode;
}