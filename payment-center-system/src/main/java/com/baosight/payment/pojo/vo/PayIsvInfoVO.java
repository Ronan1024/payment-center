package com.baosight.payment.pojo.vo;

import com.baosight.database.base.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayIsvInfoVO extends BasePO {
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
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactTel;

    /**
     * 联系人邮箱
     */
    private String contactEmail;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createBy;


    /**
     * 更新人
     */
    private Long updateBy;

}
