package com.baosight.payment.isv.convert;

import com.baosight.payment.isv.pojo.dto.CreateIsvDTO;
import com.baosight.payment.pojo.entity.PayEnterpriseInfo;
import com.baosight.payment.isv.pojo.entity.PayIsvInfo;
import com.baosight.payment.isv.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.isv.pojo.vo.PayIsvPageVO;
import com.baosight.payment.isv.vo.IsvInfoVO;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.saas.tenant.api.vo.TenantDetailInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayIsvInfoConvert {
    PayIsvInfoConvert INSTANCE = Mappers.getMapper(PayIsvInfoConvert.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", source = "payIsvInfo.createTime")
    @Mapping(target = "updateTime", source = "payIsvInfo.updateTime")
    PayIsvInfoVO toPayIsvInfoVO(PayIsvInfo payIsvInfo,PayEnterpriseInfo payEnterpriseInfo);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "tenantId", source = "id")
    @Mapping(target = "name",source = "tenantName")
    @Mapping(target = "enterpriseName", source = "firmName")
    @Mapping(target = "unifiedSocialCreditCode", source = "creditCode")
    @Mapping(target = "enterpriseStatus", source = "status")
    @Mapping(target = "enterpriseAddress", source = "residence")
    @Mapping(target = "representativeName", source = "representative")
    @Mapping(target = "representativeTel", source = "contactPhone")
    PayIsvInfoVO toPayIsvInfoVO(TenantDetailInfoVO tenantDetailInfo);

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", source = "isvId")
    @Mapping(target = "updateByName", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createByName", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    PayIsvInfo toPayIsvInfo(CreateIsvDTO createIsvDTO);

    @Mapping(target = "id", source = "isvId")
    PayEnterpriseInfo toPayEnterpriseInfo(CreateIsvDTO createIsvDTO);

    MchInfoVO toMchInfoVO(PayIsvInfoVO info);


    IsvInfoVO toIsvInfoVO(PayIsvInfo payIsvInfo);

    PayIsvPageVO toPayIsvPageVO(PayIsvInfo payIsvInfo);
}
