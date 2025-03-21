package com.baosight.payment.notify.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @TableName pay_mch_notify_config
 */
@TableName(value ="pay_mch_notify_config")
@Data
public class PayMchNotifyConfig {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 回调地址
     */
    private String notifyUrl;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;
}