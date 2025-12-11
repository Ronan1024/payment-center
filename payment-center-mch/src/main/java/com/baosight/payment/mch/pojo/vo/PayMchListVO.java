package com.baosight.payment.mch.pojo.vo;


import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 商户列表
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
public class PayMchListVO {
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

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

    @JsonIgnore
    private Long isvId;

    /**
     * 服务商名称
     */
    private String isvName;

    /**
     * 商户号
     */
    private String mchNo;


}
