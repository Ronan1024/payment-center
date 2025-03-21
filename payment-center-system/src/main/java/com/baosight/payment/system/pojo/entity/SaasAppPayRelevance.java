package com.baosight.payment.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * saas应用关联支付中心应用配置
 * @TableName saas_app_pay_relevance
 */
@TableName(value ="saas_app_pay_relevance")
@Data
public class SaasAppPayRelevance implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * saas 应用id
     */
    private Integer appId;

    /**
     * 商户id
     */
    private Long mchId;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}