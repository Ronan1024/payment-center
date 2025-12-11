package com.baosight.payment.system.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayWayVO extends BasePO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 支付方式代码 例如：WX_PAY ALI_PAY
     */
    private String payCode;

    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long updateBy;
}
