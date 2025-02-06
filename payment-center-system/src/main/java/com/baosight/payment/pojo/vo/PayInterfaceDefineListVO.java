package com.baosight.payment.pojo.vo;

import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class PayInterfaceDefineListVO {
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
