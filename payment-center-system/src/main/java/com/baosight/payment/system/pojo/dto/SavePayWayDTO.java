package com.baosight.payment.system.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SavePayWayDTO {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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

    /**
     * 支付机构
     */
    private String payingAgency;

    /**
     * 支付客户端
     */
    private Integer payingClient;

    /**
     * 支付类别
     */
    private Integer payingCategory;
    /**
     * 使用状态 true:禁用 false：启用
     */
    private Boolean disable;
}
