package com.baosight.payment.isv.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayIsvInfoVO extends BasePO {

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long tenantId;

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
     * 状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

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

}
