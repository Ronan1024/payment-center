package com.baosight.payment.settlement.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商户账户
 *
 * @author L.J.Ran
 * @TableName mch_account
 */
@Data
@TableName(value = "mch_account")
public class MchAccount {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商家id
     */
    private Long mchId;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 冻结金额
     */
    private BigDecimal accountFrozen;

    /**
     * 服务商id
     */
    private Long isvId;
}