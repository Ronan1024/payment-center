package com.baosight.payment.notify.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author L.J.Ran
 * @TableName pay_mch_notify_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pay_mch_notify_config")
public class PayMchNotifyConfig extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 上级商户id
     */
    private Long parentId;

    /**
     * 通知类型
     */
    private Integer notifyType;


    /**
     * 产品类型
     */
    private String productType;

    /**
     * 全局配置
     */
    private Boolean globalConfig;
}