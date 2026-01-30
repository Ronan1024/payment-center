package com.baosight.payment.system.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PayInterFaceDefineDTO {

    private Long id;

    /**
     * 支付接口代码
     */
    @NotBlank(message = "接口代码不能为空")
    private String code;
    /**
     * 接口名称
     */
    @NotBlank(message = "接口名称不能为空")
    private String name;

    /**
     * 支付类型ID
     */
    private Long payInterfaceTypeId;

    /**
     * 支付类型ID,如果存在多个，多个ID之间，逗号分割
     */
    private String payWay;
    /**
     * 应用场景
     */
    private Integer scenario;

    /**
     * 支付接口状态 true:开启 false：关闭
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否支持普通商户
     */
    private Boolean hasMch;

    /**
     * 是否支持服务商模式
     */
    private Boolean hasIsvMch;
    /**
     * 服务商支付参数配置
     */
    private String isvParams;

    /**
     * 特约商户配置
     */
    private String isvSubMchParams;

    /**
     * 普通商户支付参数配置
     */
    private String normalMchParams;

    /**
     * 支付机构
     */
    private String payingAgency;
}
