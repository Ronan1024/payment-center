package com.baosight.payment.system.pojo.dto.resp;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付渠道流程VO
 *
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentChannelFlowRespDTO extends BasePO {

    /**
     * 主键
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;


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
