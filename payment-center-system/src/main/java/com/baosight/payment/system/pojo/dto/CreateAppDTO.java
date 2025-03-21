package com.baosight.payment.system.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author L.J.Ran
 */
@Data
public class CreateAppDTO {

    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付方式
     */
    private List<Long> payWay;
}
