package com.baosight.payment.pojo.vo;

import com.baosight.web.serializer.CustomLongSerializer;
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
     * 创建人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long createBy;

    /**
     * 更新人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long updateBy;
}
