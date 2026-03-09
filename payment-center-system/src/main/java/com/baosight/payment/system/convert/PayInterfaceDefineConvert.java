package com.baosight.payment.system.convert;

import com.baosight.payment.system.pojo.dto.req.PayInterFaceDefineReqDTO;
import com.baosight.payment.system.pojo.dto.resp.PayingChannelDefineListRespDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayInterfaceDefineConvert {

    PayInterfaceDefineConvert INSTANCE = Mappers.getMapper(PayInterfaceDefineConvert.class);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "payingAgency", ignore = true)
    @Mapping(target = "payInterfaceTypeId", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "mchChannelUserKey", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isvParams", ignore = true)
    @Mapping(target = "isvSubMchParams", ignore = true)
    @Mapping(target = "normalMchParams", ignore = true)
    @Mapping(target = "payWay",ignore = true)
    PayInterfaceDefine toPayInterfaceDefine(PayInterFaceDefineReqDTO payInterFaceDefine);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "payingAgency", ignore = true)
    @Mapping(target = "payInterfaceTypeId", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "mchChannelUserKey", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isvParams", ignore = true)
    @Mapping(target = "isvSubMchParams", ignore = true)
    @Mapping(target = "normalMchParams", ignore = true)
    @Mapping(target = "payWay",ignore = true)
    void copyPayInterfaceDefine(@MappingTarget PayInterfaceDefine payInterfaceDefine, PayInterFaceDefineReqDTO payInterFaceDefineDTO);

    @Mapping(target = "scenario", ignore = true)
    PayInterfaceDefineVO toPayInterfaceDefineVO(PayInterfaceDefine payInterfaceDefine);

    @Mapping(target = "payingClient", ignore = true)
    @Mapping(target = "payName", ignore = true)
    @Mapping(target = "interfaceTypeName", ignore = true)
    @Mapping(target = "interfaceTypeCode", ignore = true)
    PayInterfaceDefineListVO toPayInterfaceDefineListVO(PayInterfaceDefine payInterfaceDefine);

    PayingChannelDefineListRespDTO toPayingChannelDefineListRespDTO(PayInterfaceDefine payInterfaceDefine);
}
