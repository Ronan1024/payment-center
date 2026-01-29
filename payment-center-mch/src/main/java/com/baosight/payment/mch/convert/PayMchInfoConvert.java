package com.baosight.payment.mch.convert;

import com.baosight.payment.mch.pojo.dto.MchInfoDTO;
import com.baosight.payment.mch.pojo.entity.PayBankAccountInfo;
import com.baosight.payment.mch.pojo.entity.PayMchInfo;
import com.baosight.payment.mch.pojo.vo.PayMchInfoVO;
import com.baosight.payment.pojo.entity.PayEnterpriseInfo;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.saas.tenant.api.vo.TenantDetailInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Mapper
public interface PayMchInfoConvert {
    PayMchInfoConvert INSTANCE = Mappers.getMapper(PayMchInfoConvert.class);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateByName", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createByName", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "contactTel", source = "representativeTel")
    @Mapping(target = "enterpriseName", source = "mchName")
    PayMchInfo toPayMchInfo(MchInfoDTO mchInfoDTO);


    @Mapping(target = "id", ignore = true)
    PayEnterpriseInfo toPayEnterpriseInfo(MchInfoDTO mchInfoDTO);

    @Mapping(target = "id", ignore = true)
    PayBankAccountInfo toPayBankAccountInfo(MchInfoDTO mchInfoDTO);


    PayMchInfoVO toPayMchInfoVO(PayMchInfo payMchInfo);

    @Mapping(target = "id", ignore = true)
    PayMchInfoVO toPayMchInfoVO(PayEnterpriseInfo payEnterpriseInfo,@MappingTarget PayMchInfoVO payMchInfoVO);
    @Mapping(target = "id", ignore = true)
    PayMchInfoVO toPayMchInfoVO(PayBankAccountInfo payBankAccountInfo,@MappingTarget PayMchInfoVO payMchInfoVO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenantId", source = "id")
    @Mapping(target = "mchName", source = "firmName")
    @Mapping(target = "enterpriseName", source = "firmName")
    @Mapping(target = "unifiedSocialCreditCode", source = "creditCode")
    @Mapping(target = "enterpriseStatus", source = "status")
    @Mapping(target = "enterpriseAddress", source = "residence")
    @Mapping(target = "representativeName", source = "representative")
    @Mapping(target = "representativeTel", source = "contactPhone")
    @Mapping(target = "contactTel", source = "contactPhone")
    PayMchInfoVO toPayMchInfoVO(TenantDetailInfoVO tenantDetailInfo);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateByName", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createByName", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "contactTel", source = "representativeTel")
    @Mapping(target = "enterpriseName", source = "mchName")
    void copyPayMchInfo(MchInfoDTO mchInfoDTO, @MappingTarget PayMchInfo payMchInfo);

    MchInfoVO toMchInfoVO(PayMchInfo info);
}
