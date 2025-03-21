package com.baosight.payment.system.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 支付接口配置请求体
 *
 * @author L.J.Ran
 */
@Data
public class PayInterfaceConfigDTO {

    /**
     * 支付接口id
     */
    @NotNull(message = "支付接口不能为空")
    private Long interfaceId;


    /**
     * 支付接口参数
     */
    @NotNull(message = "支付接口参数不能为空")
    private List<InterfaceParam> interfaceParam;

    /**
     * 支付费率
     */
    private Long interfaceRate;

    /**
     * 是否启用
     */
    @NotNull(message = "是否启用不能为空")
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;


    @Data
    public static class InterfaceParam {
        @NotBlank(message = "key 不能为空")
        private String name;

        @NotBlank(message = "value 不能为空")
        private String value;
    }


}
