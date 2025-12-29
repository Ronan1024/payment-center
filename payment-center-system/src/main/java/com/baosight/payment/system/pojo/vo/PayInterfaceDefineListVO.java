package com.baosight.payment.system.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付接口查询页面对象
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayInterfaceDefineListVO extends BasePO {
    @JsonSerialize(using = ToStringSerializer.class)
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
     * 状态
     */
    private Boolean enable;

    /**
     * 接口类型代码
     */
    private String interfaceTypeCode;

    /**
     * 接口类型名称
     */
    private String interfaceTypeName;

    /**
     * 支付方式名称
     */
    private String payName;


}
