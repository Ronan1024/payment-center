package com.baosight.payment.channel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 支付渠道表
 * @TableName channel
 */
@TableName(value ="channel")
@Data
public class Channel {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 渠道编码，如 wxpay/alipay/allinpay
     */
    private String channelCode;

    /**
     * 渠道名称
     */
    private String channelName;

    /**
     * 渠道类型
     */
    private String channelType;

    /**
     * 机构名称
     */
    private String institutionName;

    /**
     * 渠道处理器
     */
    private String handlerFamily;

    /**
     * 是否支持支付
     */
    private Boolean supportPayment;

    /**
     * 是否支持退款
     */
    private Boolean supportRefund;

    /**
     * 是否支持查单
     */
    private Boolean supportQuery;

    /**
     * 是否支持关单
     */
    private Boolean supportClose;

    /**
     * 是否支持账单
     */
    private Boolean supportBill;

    /**
     * 是否支持会员能力
     */
    private Boolean supportMember;

    /**
     * 是否支持商户进件
     */
    private Boolean supportMerchantEntry;

    /**
     * 渠道状态状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;
}