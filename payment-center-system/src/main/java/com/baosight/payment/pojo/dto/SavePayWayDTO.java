package com.baosight.payment.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SavePayWayDTO {

    /**
     * 支付方式代码 例如：WX_PAY ALI_PAY
     */
    @NotBlank(message = "支付方式code不能为空")
    private String payCode;

    /**
     * 支付方式名称
     */
    @NotBlank(message = "支付方式名称不能为空")
    private String payName;

}
