package com.baosight.payment.system.pojo.dto.req;

import com.baosight.payment.system.utils.DynamicFormUtil;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import static com.baosight.payment.system.error.PayInterfaceError.*;

/**
 * @author L.J.Ran
 */
@Data
public class PayInterFaceDefineReqDTO {

    /**
     * 支付渠道编号
     */
    @NotBlank(message = "接口代码不能为空")
    private String code;

    /**
     * 支付方式
     */
    @NotNull(message = "支付方式不能为空")
    private List<Long> payWay;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否支持普通商户
     */
    private Boolean hasMch;

    /**
     * 是否开启
     */
    private Boolean enable;

    /**
     * 是否支持服务商模式
     */
    private Boolean hasIsvMch;

    /**
     * 服务商支付参数配置
     */
    private List<DynamicFormUtil.DynamicForm> isvParams;

    /**
     * 特约商户配置
     */
    private List<DynamicFormUtil.DynamicForm> isvSubMchParams;

    /**
     * 普通商户支付参数配置
     */
    private List<DynamicFormUtil.DynamicForm> normalMchParams;

    public void verify(){
        if (Boolean.TRUE.equals(this.getHasMch())) {
            Assert.isNull(this.getNormalMchParams(), ApiException.supplier(PAY_INTERFACE_NORMAL_MCH_PARAMS_NULL));
        }
        if (Boolean.TRUE.equals(this.getHasIsvMch())) {
            Assert.isNull(this.getIsvSubMchParams(),  ApiException.supplier(PAY_INTERFACE_ISV_SUB_MCH_PARAMS_NULL));
            Assert.isNull(this.getIsvParams(), ApiException.supplier(PAY_INTERFACE_ISV_PARAMS_NULL));
        }
    }

}
