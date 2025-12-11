package com.baosight.payment.notify.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 商户通知记录表
 *
 * @TableName pay_mch_notify_record
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pay_mch_notify_record")
public class PayMchNotifyRecord extends BasePO {
    /**
     * 商户通知记录ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单类型:1-支付,2-退款
     */
    private Integer orderType;

    /**
     * 商户订单号
     */
    private String mchOrderNo;

    /**
     * 商户id
     */
    private Long mchId;

    /**
     * 服务商id
     */
    private Long isvId;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 服务商号
     */
    private String isvNo;

    /**
     * 应用ID
     */
    private Long appId;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 通知响应结果
     */
    private String resResult;

    /**
     * 通知次数
     */
    private Integer notifyCount;

    /**
     * 最大通知次数, 默认7次
     */
    private Integer notifyCountLimit;

    /**
     * 通知状态,1-通知中,2-通知成功,3-通知失败
     */
    private Integer state;

    /**
     * 最后一次通知时间
     */
    private Date lastNotifyTime;

    /**
     * 通知类型
     */
    private Integer notifyType;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 下一次通知时间
     */
    private Date nextNotifyTime;
}