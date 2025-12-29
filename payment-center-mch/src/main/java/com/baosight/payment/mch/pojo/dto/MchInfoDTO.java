package com.baosight.payment.mch.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 创建商户信息请求体
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
public class MchInfoDTO {

    /**
     * 租户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long tenantId;

    /**
     * 联系人
     */
    private String contactName;

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
    @NotBlank(message = "企业名称不能为空")
    private String mchName;

//    /**
//     * 商户简称
//     */
//    @NotBlank(message = "商户简称不能为空")
//    private String mchShortName;

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
     * 备注
     */
    private String remark;


}
