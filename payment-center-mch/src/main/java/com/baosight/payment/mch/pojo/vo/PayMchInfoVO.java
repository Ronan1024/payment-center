package com.baosight.payment.mch.pojo.vo;


import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayMchInfoVO extends BasePO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户号
     */
    private String mchNo;

    /**
     * 租户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;

    /**
     * 租户ID
     */
    private String contactName;
    /**
     * 联系电话
     */
    private String contactTel;

    /**
     * 商户类型 2.普通商户 3.特约商户
     */
    private Integer type;

    /**
     * 商户状态 0：停用 1：正常
     */
    private Integer state;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long isvId;

    /**
     * 服务商名称
     */
    private String isvName;

    /**
     * 商户名称
     */
    private String mchName;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 企业性质
     */
    private Integer enterpriseDominantType;

    /**
     * 社会统一信用码
     */
    private String unifiedSocialCreditCode;

    /**
     * 企业状态
     */
    private Integer enterpriseStatus;

    /**
     * 证件类型
     */
    private Integer certificateType;

    /**
     * 企业地址
     */
    private String enterpriseAddress;

    /**
     * 所属行业
     */
    private Integer enterpriseIndustry;

    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 法人名称
     */
    private String representativeName;

    /**
     * 法人性别
     */
    private Integer representativeSex;
    /**
     * 法人手机号
     */
    private String representativeTel;
    /**
     * 证件类型
     */
    private Integer representativeCertificateType;
    /**
     * 证件号码
     */
    private String representativeCertificateId;
    /**
     * 证件有效起始日期
     */
    private Date representativeCertificateEffectStartTime;
    /**
     * 证件有效截至日期
     */
    private Date representativeCertificateEffectEndTime;
    /**
     * 开户行名称
     */
    private String bankName;
    /**
     * 开户网点名称
     */
    private String branchName;
    /**
     * 账户名
     */
    private String accountName;
    /**
     * 账户号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long accountId;
    /**
     * 省ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long provinceId;
    /**
     * 省
     */
    private String province;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cityId;
    /**
     * 市
     */
    private String city;
    /**
     * 区域ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long areaId;
    /**
     * 开户所在区
     */
    private String area;
    /**
     * 备注
     */
    private String remark;
}
