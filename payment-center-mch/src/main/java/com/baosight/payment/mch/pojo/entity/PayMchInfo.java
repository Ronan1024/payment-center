package com.baosight.payment.mch.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 支付服务商信息表
 *
 * @author L.J.Ran
 * @TableName pay_mch_info
 */
@Data
@TableName(value = "pay_mch_info")
@EqualsAndHashCode(callSuper = true)
public class PayMchInfo extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 商户号
     */
    private String mchNo;
    /**
     * 商户号
     */
    private String mchName;
    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 租户联系人
     */
    private String contactName;

    /**
     * 商户类型 2.普通商户 3.特约商户
     */
    private Integer type;

    /**
     * 商户状态 0：停用 1：正常
     */
    private Integer state;

    /**
     * 服务商ID
     */
    private Long isvId;
    /**
     * 服务商名称
     */
    private String isvName;

    /**
     * 企业信息id
     */
    private Long enterpriseInfoId;

    /**
     * 商户银行信息id
     */
    private Long bankAccountInfoId;

    /**
     * 联系电话（冗余字段，用于页面查询）
     */
    private String contactTel;

    /**
     * 企业名称（冗余字段，用于页面查询）
     */
    private String enterpriseName;

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
     * 备注
     */
    private String remark;
}