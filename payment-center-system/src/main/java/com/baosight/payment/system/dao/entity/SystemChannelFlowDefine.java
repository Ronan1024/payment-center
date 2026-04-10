package com.baosight.payment.system.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付渠道流程管理
 *
 * @author L.J.Ran
 * @TableName payment_channel_flow
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_channel_flow_define")
public class SystemChannelFlowDefine extends BasePO {
    /**
     *
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;


    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道id
     */
    private Long channelId;


    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 步骤顺序
     */
    private Integer stepOrder;

    /**
     * 步骤类型与后台代码一致
     */
    private String stepType;

    /**
     * 用户交互组件
     */
    private String userInput;

    /**
     * 步骤执行参数
     */
    private String stepParams;

    /**
     * 客户端类型
     */
    private Integer clientType;
}