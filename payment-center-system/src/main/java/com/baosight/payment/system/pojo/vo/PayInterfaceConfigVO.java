package com.baosight.payment.system.pojo.vo;

import com.baosight.database.base.BasePO;
import com.baosight.saas.entity.DynamicForm;
import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayInterfaceConfigVO extends BasePO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 客户端类型
     */
    private Integer clientType;

    private String name;

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long clientId;

    /**
     * 支付接口id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long interfaceId;

    /**
     * 支付接口参数
     */
    private String interfaceParams;

    /**
     * 支付接口参数obj
     */
    private List<DynamicForm> interfaceParam;

    /**
     * 支付接口费率
     */
    private Long interfaceRate;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 备注信息
     */
    private String remark;

    private Long createBy;

    private Long updateBy;

    /**
     * 支付机构
     */
    private String payingAgency;

    /**
     * 是否配置
     */
    @JsonIgnore
    private Boolean hasSetting;
}
