package com.baosight.payment.system.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务商支付配置请求体
 *
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientPayInterfaceConfigDTO extends PayInterfaceConfigDTO {

    /**
     * 服务商或商家id
     */
    @NotNull(message = "客户端信息不能为空")
    private Long clientId;
}
