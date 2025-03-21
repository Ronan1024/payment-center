package com.baosight.payment.system.pojo.vo;

import com.baosight.database.base.BasePO;
import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author longjiangran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayInterfaceConfigListVO extends BasePO {

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 支付配置名称
     */
    private String name;

    /**
     * 是否开通
     */
    private Boolean enable;

    /**
     * 图标
     */
    private String icon;
}
