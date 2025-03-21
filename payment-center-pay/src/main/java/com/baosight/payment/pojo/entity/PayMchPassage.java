package com.baosight.payment.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 商户支付通道表
 * @TableName pay_mch_passage
 */
@Data
@TableName(value ="pay_mch_passage")
public class PayMchPassage implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 支付接口
     */
    private Long interfaceId;

    /**
     * 支付方式
     */
    private String payWayCode;

    /**
     * 支付方式费率
     */
    private BigDecimal rate;

    /**
     * 风控数据
     */
    private Object riskConfig;

    /**
     * 状态: 0-停用, 1-启用
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}