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
 * 支付接口配置
 *
 * @author L.J.Ran
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
     * 商户号
     */
    private String mchNo;

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
    /**
     * 支付名称
     */
    private String name;

    private Long updateBy;
    /**
     * 支付机构
     */
    private String payingAgency;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 商户渠道账号表示 如何微信AppId
     */
    private String mchChannelUser;

    /**
     * 支付接口code
     */
    private String interfaceCode;

    /**
     * 上级客户端id
     */
    private Long parentClientId;

    /**
     * 上级客户端编号
     */
    private String parentClientCode;
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}