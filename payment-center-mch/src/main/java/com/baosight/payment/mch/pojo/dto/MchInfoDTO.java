package com.baosight.payment.mch.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
     * 企业名称
     */
    @NotBlank(message = "企业名称不能为空")
    private String enterpriseName;
    /**
     * 企业性质
     */
    private Integer enterpriseDominantType;

    /**
     * 社会统一信用码
     */
    @NotBlank(message = "社会统一信用码不能为空")
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
    @NotBlank(message = "企业地址不能为空")
    private String enterpriseAddress;

    /**
     * 所属行业
     */
    private Integer enterpriseIndustry;

    /**
     * 经营范围
     */
    @NotBlank(message = "经营范围不能为空")
    private String businessScope;

    /**
     * 法人名称
     */
    @NotBlank(message = "法人名称不能为空")
    private String representativeName;

    /**
     * 法人性别
     */
    private Integer representativeSex;
    /**
     * 法人手机号
     */
    @NotBlank(message = "法人手机号不能为空")
    private String representativeTel;
    /**
     * 证件类型
     */
    private Integer representativeCertificateType;
    /**
     * 证件号码
     */
    @NotBlank(message = "证件号码不能为空")
    private String representativeCertificateId;
    /**
     * 证件有效起始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @NotNull(message = "证件有效期结束时间不能为空")
    private Date representativeCertificateEffectStartTime;
    /**
     * 证件有效截至日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
//    @NotBlank(message = "证件有效截至日期不能为空")
    @NotNull(message = "证件有效截至日期不能为空")
    private Date representativeCertificateEffectEndTime;
    /**
     * 开户行名称
     */
    @NotBlank(message = "开户行名称不能为空")
    private String bankName;
    /**
     * 开户网点名称
     */
    @NotBlank(message = "开户网点名称不能为空")
    private String branchName;
    /**
     * 账户名
     */
    @NotBlank(message = "账户名不能为空")
    private String accountName;
    /**
     * 账户号
     */
    @NotNull(message = "账户号不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long accountId;
    /**
     * 省ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long provinceId;
    /**
     * 开户所在省
     */
    private String province;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cityId;
    /**
     * 开户所在市
     */
    private String city;
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
