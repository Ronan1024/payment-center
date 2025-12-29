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
     * 支付类别
     */
    private Integer payingCategory;

}
