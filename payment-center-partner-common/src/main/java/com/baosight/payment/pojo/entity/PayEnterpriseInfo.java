package com.baosight.payment.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 企业信息实体类
 * 对应数据库中企业信息相关表结构
 *
 * @author 开发者名称
 * @date 生成日期
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayEnterpriseInfo extends BasePO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 企业性质 取值参见EnterpriseDominantType
     */
    private Byte enterpriseDominantType;

    /**
     * 社会统一信用代码
     */
    private String unifiedSocialCreditCode;

    /**
     * 企业状态 1。存续 2.异常 3.注销 4.冻结
     */
    private Integer enterpriseStatus;

    /**
     * 证件类型 7.营业执照 8.组织机构代码证 9.税务登记证 10.统一社会信用代码
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
     * 法人姓名
     */
    private String representativeName;

    /**
     * 法人性别(1.男 2.女）
     */
    private Integer representativeSex;

    /**
     * 法人手机号
     */
    private String representativeTel;

    /**
     * 证件类型 1.身份证 2.护照 3。军官证 4。士兵证 5。港澳居民来往内地通行证 6。台湾同胞往来内地通行证 7.营业执照
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
     * 证件有效终止日期
     */
    private Date representativeCertificateEffectEndTime;
}