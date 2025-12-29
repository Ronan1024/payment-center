package com.baosight.payment.mch.pojo.vo;


import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 租户账号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;

    /**
     * 企业名称
     */
    private String mchName;

    /**
     * 商户状态
     */
    private Integer state;

    /**
     * 商户类型（2.普通商户 3.特约商户）
     */
    private Integer type;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系人电话
     */
    private String contactTel;


}
