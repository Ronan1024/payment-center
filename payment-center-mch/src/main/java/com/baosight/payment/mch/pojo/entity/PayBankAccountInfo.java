package com.baosight.payment.mch.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 银行账户信息实体类
 * 对应数据库中银行账户相关表结构，存储开户行、账户信息等核心数据
 *
 * @author 开发者
 * @date 2025-12-17
 */
@Data
public class PayBankAccountInfo {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 开户网点名称
     */
    private String branchName;

    /**
     * 账户名
     */
    private String accountName;

    /**
     * 账户号
     */
    private Long accountId;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 省
     */
    private String province;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 城市
     */
    private String city;
}