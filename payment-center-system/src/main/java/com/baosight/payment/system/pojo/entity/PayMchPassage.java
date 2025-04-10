package com.baosight.payment.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商户支付通道表
 * @author L.J.Ran
 * @TableName pay_mch_passage
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="pay_mch_passage")
public class PayMchPassage extends BasePO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商户号
     */
    private Long mchId;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 支付接口
     */
    private Long interfaceId;

    /**
     * 支付接口编号
     */
    private String interfaceCode;

    /**
     * 支付方式id
     */
    private Long payWayId;
    /**
     * 支付方式
     */
    private String payWayCode;

    /**
     * 支付方式费率
     */
    private Long rate;

    /**
     * 风控数据
     */
    private Object riskConfig;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新人名称
     */
    private String updateByName;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createByName;
}