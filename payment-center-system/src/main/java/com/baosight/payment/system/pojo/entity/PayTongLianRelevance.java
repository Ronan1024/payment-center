package com.baosight.payment.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;

import lombok.Data;

/**
 * 通联支付扩展关联信息
 *
 * @TableName pay_tong_lian_relevance
 */
@TableName(value = "pay_tong_lian_relevance")
@Data
public class PayTongLianRelevance implements Serializable {
    /**
     *
     */
    @TableId
    private Long id;

    /**
     * 收银宝账户
     */
    private String sybMerchantCode;

    /**
     * 是否绑定手机号
     */
    private Boolean hasBindPhone;

    /**
     * 绑定的手机号
     */
    private String phone;

    /**
     * 是否绑定收银宝
     */
    private Boolean hasBindSyb;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 通联合约签订
     */
    private Boolean hasContractSign;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}