package com.baosight.payment.settlement.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/22
 */
@Data
public class MchAccountRecordVO {
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
