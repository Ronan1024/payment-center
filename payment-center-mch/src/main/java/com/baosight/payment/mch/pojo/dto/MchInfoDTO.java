package com.baosight.payment.mch.pojo.dto;

import com.baosight.utils.annotation.Mobile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @program: payment-center
 * @description: 创建商户信息请求体
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
public class MchInfoDTO {

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空")
    private String mchName;

    /**
     * 商户简称
     */
    @NotBlank(message = "商户简称不能为空")
    private String mchShortName;

    /**
     * 联系人名称
     */
    @NotBlank(message = "联系人名称不能为空")
    private String contactName;

    /**
     * 联系人电话
     */
    @Mobile
    @NotBlank(message = "联系人电话不能为空")
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 商户类型
     */
    @NotNull(message = "商户类型不能为空")
    private Integer type;

    /**
     * 服务商
     */
    private Long isvId;
}
