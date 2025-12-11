package com.baosight.payment.mch.pojo.vo;


import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayMchInfoVO extends BasePO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 服务商名称
     */
    private String mchName;

    /**
     * 服务商简称
     */
    private String mchShortName;

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
     * 状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long createBy;

    /**
     * 更新人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long updateBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 更新人名称
     */
    private String updateByName;

    /**
     * 商户类型
     */
    private Integer type;

    /**
     * 服务商id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long isvId;

    private String isvName;

    /**
     * 商户号
     */
    private String mchNo;
}
