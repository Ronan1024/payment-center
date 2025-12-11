package com.baosight.payment.system.pojo.vo;

import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class PayWayPageVO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 支付方式代码 例如：WX_PAY ALI_PAY
     */
    private String payCode;

    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 可用支付客户端
     */
    private Integer payingClient;

    /**
     * 创建人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long createBy;

    /**
     * 更新人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long updateBy;

    private String payingAgency;

    private String payingAgencyName;

    /**
     * 创建人名称
     */
    private String createByName;


    /**
     * 更新人名称
     */
    private String updateByName;
}
