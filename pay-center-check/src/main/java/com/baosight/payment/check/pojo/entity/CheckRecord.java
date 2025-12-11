package com.baosight.payment.check.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 对账记录
 * @TableName check_record
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="check_record")
public class CheckRecord extends BasePO {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long orderId;

    /**
     * 金额
     */
    private Long amount;

    /**
     * 交易类型
     */
    private String tradeType;

    /**
     * 渠道订单号
     */
    private String channelOrderId;

    /**
     * 渠道交易金额
     */
    private Long channelAmount;

    /**
     * 对账状态
     */
    private Integer checkState;


    private Long  checkBatchId;

    private String checkBatchCode;
}