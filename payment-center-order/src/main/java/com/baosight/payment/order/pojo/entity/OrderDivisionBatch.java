package com.baosight.payment.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单分账批次
 *
 * @TableName order_division_batch
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "order_division_batch")
public class OrderDivisionBatch extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分账批次时间
     */
    private String date;

    /**
     * 分账状态
     */
    private Integer divisionState;

    /**
     * 客户端id
     */
    private Long clientId;

    /**
     * 失败原因
     */
    private String failMsg;
}