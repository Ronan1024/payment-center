package com.baosight.payment.system.pojo.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 保存支付渠道流程DTO
 *
 * @author L.J.Ran
 */
@Data
public class SavePaymentChannelFlowReqDTO {

    /**
     * 渠道编号
     */
    @NotBlank(message = "渠道编号不能为空")
    private String channelCode;


    /**
     * 流程名称
     */
    @NotBlank(message = "流程名称不能为空")
    private String flowName;

    /**
     * 步骤顺序
     */
    @NotNull(message = "步骤顺序不能为空")
    private Integer stepOrder;

    /**
     * 步骤类型与后台代码一致
     */
    @NotBlank(message = "步骤类型不能为空")
    private String stepType;


    /**
     * 客户端类型
     */
    private Integer clientType;



    /**
     * 用户交互组件
     */
    private String userInput;

    /**
     * 步骤执行参数
     */
    private String stepParams;
}
