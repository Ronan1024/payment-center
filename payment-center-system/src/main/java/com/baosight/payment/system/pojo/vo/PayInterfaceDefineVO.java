package com.baosight.payment.system.pojo.vo;

import com.baosight.saas.entity.DynamicForm;
import com.baosight.web.serializer.CustomLongSerializer;
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
     * 接口名称
     */
    private String name;

    /**
     * 是否支持普通商户
     */
    private Boolean hasMch;

    /**
     * 是否支持服务商子商户
     */
    private Boolean hasSubMch;

    /**
     * 服务商支付参数配置
     */
    private List<DynamicForm> facilitatorParams;

    /**
     * 子商户支付参数配置
     */
    private List<DynamicForm> subMchParams;

    /**
     * 普通商户支付参数配置
     */
    private List<DynamicForm> normalMchParams;

    /**
     * 备注
     */
    private String remark;


    /**
     * 创建人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long createBy;


    /**
     * 更新人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long updateBy;
    /**
     * 支付方式
     */
    private List<String> payWayList;
    /**
     * 是否开启
     */
    private Boolean enable;


    /**
     * 支付接口编号
     */
    private String code;

    /**
     * 支付渠道用户key
     */
    private String mchChannelUserKey;

}
