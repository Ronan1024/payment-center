package com.baosight.payment.mch.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 商户应用表
 * @TableName pay_mch_app
 */
@TableName(value ="pay_mch_app")
@Data
public class PayMchApp {
    /**
     * 应用ID
     */
    @TableId
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 商户号
     */
    private Long mchId;

    /**
     * 应用状态
     */
    private Integer state;

    /**
     * 应用私钥
     */
    private String appSecret;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者用户ID
     */
    private Long createBy;

    /**
     * 创建者姓名
     */
    private String createdByName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}