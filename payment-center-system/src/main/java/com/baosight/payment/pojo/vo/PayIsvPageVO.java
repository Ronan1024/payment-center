package com.baosight.payment.pojo.vo;

import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayIsvPageVO extends BasePO {
    private Long id;
    /**
     * 服务商名称
     */
    private String name;

    /**
     * 服务商简称
     */
    private Integer shortName;


    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 创建人
     */
    private Long createBy;


    /**
     * 更新人
     */
    private Long updateBy;

}
