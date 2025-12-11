package com.baosight.payment.system.pojo.vo;

import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/19
 */
@Data
public class PayWayListVO {
    /**
     * 支付方式代码 例如：WX_PAY ALI_PAY
     */
    private String payCode;

    /**
     * 支付方式名称
     */
    private String payName;

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

}
