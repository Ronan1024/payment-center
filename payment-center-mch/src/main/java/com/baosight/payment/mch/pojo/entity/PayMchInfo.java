package com.baosight.payment.mch.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
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
     * 服务商名称
     */
    private String mchName;

    /**
     * 服务商简称
     */
    private String mchShortName;

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
     * 状态
     */
    private Integer state;

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

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 更新人名称
     */
    private String updateByName;

    /**
     * 商户类型
     */
    private Integer type;

    /**
     * 服务商id
     */
    private Long isvId;

    /**
     * 商户号
     */
    private String mchNo;
}