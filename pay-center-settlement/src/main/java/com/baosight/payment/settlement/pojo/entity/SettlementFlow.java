package com.baosight.payment.settlement.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 结算流水单表
 * @author L.J.Ran
 * @TableName settlement_flow
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value ="settlement_flow")
public class SettlementFlow extends BasePO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 结算受理单
     */
    private Long requestId;

    /**
     * 结算账期ID(当归集后进行填充)
     */
    private Long periodId;

    /**
     * 结算金额
     */
    private Integer amount;

    /**
     * 手续费
     */
    private Integer fee;

    /**
     * 结算流水类型
     */
    private Integer type;

    /**
     * 结算流水状态
     */
    private Integer state;

    /**
     * 结算时间
     */
    private Date settleTime;
}