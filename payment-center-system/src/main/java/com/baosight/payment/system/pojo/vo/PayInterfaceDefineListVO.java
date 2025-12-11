package com.baosight.payment.system.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayInterfaceDefineListVO extends BasePO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 图标
     */
    private String icon;
}
