package com.baosight.payment.adapter.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 通道适配器运行控制记录。
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "pay_channel_adapter_control")
public class PayChannelAdapterControl extends BasePO {

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
     * 支付渠道编码。
     */
    private String channelCode;

    /**
     * 签约模式编码。
     */
    private String modeCode;

    /**
     * 渠道接口编码。
     */
    private String interfaceCode;

    /**
     * 运行状态：ENABLED、DISABLED、MAINTENANCE、DEGRADED。
     */
    private String runtimeStatus;

    /**
     * 影响范围，多个范围使用英文逗号分隔。
     */
    private String effectScope;

    /**
     * 禁用、维护或降级原因。
     */
    private String disabledReason;

    /**
     * 操作人。
     */
    private String disabledBy;

    /**
     * 操作时间。
     */
    private LocalDateTime disabledAt;

    /**
     * 计划恢复时间。
     */
    private LocalDateTime recoverAt;

    /**
     * 是否自动恢复。
     */
    private Boolean autoRecover;

    /**
     * 乐观锁版本号。
     */
    private Integer version;
}
