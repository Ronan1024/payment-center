package com.baosight.payment.isv.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author L.J.Ran
 */
@Data
public class CreateIsvDTO {

    /**
     * 服务商id
     */
    private Long isvId;

    /**
     * 服务商名称
     */
    @NotNull(message = "服务商名称不能为空")
    private String name;

    /**
     * 服务商简称
     */
    private String shortName;

    /**
     * 联系人名称
     */
    @NotNull(message = "联系人名称不能为空")
    private String contactName;

    /**
     * 联系人电话
     */
    @NotNull(message = "联系人电话不能为空")
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 是否启用
     */
    @NotNull(message = "是否启用不能为空")
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;
}
