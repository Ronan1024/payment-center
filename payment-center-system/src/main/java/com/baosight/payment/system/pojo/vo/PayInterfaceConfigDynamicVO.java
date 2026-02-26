package com.baosight.payment.system.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.saas.entity.DynamicForm;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author L.J.Ran
 */
@Data
public class PayInterfaceConfigDynamicVO extends BasePO{
    /**
     * 主键ID（pay_interface_config表的主键）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long clientId;
    /**
     * 支付名称（如微信支付、支付宝）
     */
    private String name;
    /**
     * 启用状态
     */
    private Boolean enable;

    /**
     * 支付费率
     */
    private Long interfaceRate;

    /**
     * 备注
     */
    private String remark;

    /**
     * key：标签名称（英文）
     * value:标签取值
     */
    private List<DynamicForm> paramList;





}
