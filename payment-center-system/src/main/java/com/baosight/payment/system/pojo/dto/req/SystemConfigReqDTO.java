package com.baosight.payment.system.pojo.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/10
 */
@Data
public class SystemConfigReqDTO {
    /**
     * 配置值
     */
    @NotBlank(message = "配置值不能为空")
    private Object value;
}
