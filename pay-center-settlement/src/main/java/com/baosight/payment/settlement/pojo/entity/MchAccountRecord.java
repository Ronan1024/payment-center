package com.baosight.payment.settlement.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商家账户记录
 *
 * @author L.J.Ran
 * @TableName mch_account_record
 */
@Data
@TableName(value = "mch_account_record")
public class MchAccountRecord {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     *
     */
    private BigDecimal amount;

    /**
     *
     */
    private Date createTime;

    /**
     * 钱包流水类型
     */
    private Integer type;

    /**
     * 商家id
     */
    private Long mchId;

    /**
     * 申请订单号
     */
    private String applyOrderId;


    /**
     * 申请状态
     */
    private Integer applyState;

    /**
     * 申请异常信息
     */
    private String applyMsg;

    /**
     * 通知地址
     */
    private String notifyUrl;

    /**
     * 商家订单号
     */
    private String mchOrderId;
}