package com.baosight.payment.order.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单分账记录
 * @author L.J.Ran
 * @TableName order_division_record
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="order_division_record")
public class OrderDivisionRecord extends BasePO {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    private Long batchId;

    /**
     * 
     */
    private Long orderId;

    /**
     * 
     */
    private Integer state;

    /**
     * 
     */
    private String errMsg;

    /**
     * 
     */
    private String date;
}