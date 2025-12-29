package com.baosight.payment.system.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * <p>
 * 接口类型配置表
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
@Data
public class PayInterfaceTypeDTO {

    private Long id;

    /**
     * 接口类型代码
     */
    @NotBlank(message = "接口类型代码不能为空")
    private String interfaceTypeCode;

    /**
     * 接口类型名称
     */
    @NotBlank(message = "接口类型名称不能为空")
    private String interfaceTypeName;

    /**
     * 是否禁用
     */
    @NotNull(message = "是否禁用不能为空")
    private Boolean disable;

    /**
     * 支持的配置方式
     */
    private String configWay;

    /**
     * 备注
     */
    private String remark;

    /**
     * 配置定义描述
     */
    private String description;

    /**
     * 回调白名单
     */
    private String callbackIp;

    /**
     * 是否开启回调
     */
    private Boolean isCallback;

}
