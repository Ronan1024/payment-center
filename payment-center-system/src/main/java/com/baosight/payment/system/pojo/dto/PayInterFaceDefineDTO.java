package com.baosight.payment.system.pojo.dto;

import com.baosight.saas.entity.DynamicForm;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PayInterFaceDefineDTO {
    /**
     * 支付名称
     */
    @NotBlank(message = "接口名称不能为空")
    private String name;

    /**
     * 是否支持普通商户
     */
    @NotNull(message = "是否支持普通商户不能为空")
    private Boolean hasMch;

    /**
     * 是否支持子商户
     */
    @NotNull(message = "是否服务商子商户不能为空")
    private Boolean hasSubMch;

    /**
     * 服务商支付参数
     */
    private List<DynamicForm> facilitatorParams;

    /**
     * 子商户支付参数
     */
    private List<DynamicForm> subMchParams;

    /**
     * 普通商户支付参数
     */
    private List<DynamicForm> normalMchParams;

    /**
     * 备注
     */
    private String remark;

    /**
     * 支付方式列表
     */
    @NotNull(message = "支付方式不能为空")
    private List<Long> payWayList;

    /**
     * 是否开启
     */
    @NotNull(message = "是否开启不能为空")
    private Boolean enable;

    /**
     * 支付方式图标
     */
//    @NotBlank(message = "支付方式图标不能为空")
    private String icon;

    /**
     * 支付接口编号
     */
    @NotBlank(message = "支付接口编号不能为空")
    private String code;

    /**
     * 支付渠道用户key
     */
    @NotBlank(message = "支付渠道用户key不能为空")
    private String mchChannelUserKey;
}
