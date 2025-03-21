package com.baosight.payment.isv.pojo.vo;

import com.baosight.database.base.BasePO;
import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayIsvPageVO extends BasePO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;
    /**
     * 服务商名称
     */
    private String name;

    /**
     * 服务商简称
     */
    private String shortName;


    /**
     * 状态
     */
    private Integer state;

    /**
     * 创建人
     */
    private Long createBy;


    /**
     * 更新人
     */
    private Long updateBy;

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

}
