package com.baosight.payment.system.convert;

import com.baosight.payment.dao.TongLianConfigVO;
import com.baosight.payment.system.pojo.dto.PayInterfaceConfigDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigDynamicVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.vo.IsvInterfaceConfigVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayInterfaceConfigConvert {
    PayInterfaceConfigConvert INSTANCE = Mappers.getMapper(PayInterfaceConfigConvert.class);

    @Mapping(target = "isvId", ignore = true)
    @Mapping(target = "hasSetting", ignore = true)
//    @Mapping(target = "interfaceParam", ignore = true)
    PayInterfaceConfigVO toClientPayInterfaceConfigVo(PayInterfaceConfig payInterfaceConfig);

    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "payingAgency", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "interfaceParams", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "clientType", ignore = true)
    PayInterfaceConfig toEntity(PayInterfaceConfigDTO payInterfaceConfigDTO);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "interfaceParams", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "clientType", ignore = true)
    void copyPayInterfaceConfig(@MappingTarget PayInterfaceConfig payInterfaceConfig, PayInterfaceConfigDTO payInterfaceConfigDTO);

    @Mapping(target = "icon", ignore = true)
    PayInterfaceConfigListVO toPayInterfaceConfigListVO(PayInterfaceDefineListVO payInterfaceDefineListVO);

    TongLianConfigVO toTongLianConfigVO(PayInterfaceConfig interfaceConfig);

    @Mapping(target = "mchFeeRate", source = "interfaceRate")
    @Mapping(target = "thirdCode", source = "mchChannelUser")
    @Mapping(target = "mchType", source = "clientType")
    @Mapping(target = "mchId", source = "clientId")
    @Mapping(target = "config", ignore = true)
    MchInterfaceConfigVO toMchInterfaceConfigVO(PayInterfaceConfig payInterfaceConfig);

    @Mapping(target = "isvId", source = "clientId")
    @Mapping(target = "isvNo", ignore = true)
    @Mapping(target = "interfaceName", source = "name")
    @Mapping(target = "config", ignore = true)
    IsvInterfaceConfigVO toIsvInterfaceConfigVO(PayInterfaceConfig payInterfaceConfig);

    PayInterfaceConfig toPayInterfaceConfig(PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO);
}
