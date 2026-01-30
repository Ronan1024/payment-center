package com.baosight.payment.isv.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * @author L.J.Ran
 */
@Data
public class CreateIsvDTO {

    /**
     * 服务商id
     */
    private Long isvId;

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
    @NotNull(message = "联系人名称不能为空")
    private String contactName;

    /**
     * 联系人电话
     */
    @NotNull(message = "联系人电话不能为空")
    private String contactTel;

    /**
     * 是否启用
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    // 企业信息
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date representativeCertificateEffectStartTime;
    /**
     * 证件有效截至日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date representativeCertificateEffectEndTime;
}
