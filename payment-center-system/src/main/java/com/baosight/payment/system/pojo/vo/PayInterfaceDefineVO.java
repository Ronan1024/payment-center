package com.baosight.payment.system.pojo.vo;

import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @author L.J.Ran
 */
@Data
public class PayInterfaceDefineVO {

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;
    /**
     * 支付接口代码
     */
    private String code;
    /**
     * 接口名称
     */
    private String name;

    /**
     * 支付类型ID
     */
    private Long payWayId;
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
     * 支付渠道用户key
     */
    private String mchChannelUserKey;

}
