package com.baosight.payment.mch.pojo.vo;


import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description: 支付商户列表
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayMchListVO extends BasePO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户编号
     */
    private String mchNo;

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

    /**
     * 服务商名称
     */
    private String isvName;

    /**
     *  服务商id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long isvId;

    /**
     * 服务商编号
     */
    private String isvCode;


}
