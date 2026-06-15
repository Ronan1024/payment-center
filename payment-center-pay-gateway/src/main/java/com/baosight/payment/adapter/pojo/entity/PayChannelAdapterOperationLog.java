package com.baosight.payment.adapter.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通道适配器运行控制操作日志。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pay_channel_adapter_operation_log")
public class PayChannelAdapterOperationLog extends BasePO {

    /**
     * 主键。
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 适配器唯一键。
     */
    private String adapterKey;

    /**
     * 操作类型：DISABLE、ENABLE、MAINTENANCE。
     */
    private String operationType;

    /**
     * 操作后的运行状态。
     */
    private String runtimeStatus;

    /**
     * 操作影响范围。
     */
    private String effectScope;

    /**
     * 操作原因。
     */
    private String reason;

    /**
     * 操作人。
     */
    @TableField("operator_name")
    private String operator;
}
