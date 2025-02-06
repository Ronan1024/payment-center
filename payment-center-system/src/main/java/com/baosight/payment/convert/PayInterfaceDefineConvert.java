package com.baosight.payment.convert;

import com.baosight.payment.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.pojo.entity.PayIsvInfo;
import com.baosight.payment.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.pojo.vo.PayIsvInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayInterfaceDefineConvert {

    PayInterfaceDefineConvert INSTANCE = Mappers.getMapper(PayInterfaceDefineConvert.class);

    @Mapping(target = "hasIsvMch", source = "hasSubMch")
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "payWay", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "isvParams", ignore = true)
    @Mapping(target = "isvSubMchParams", ignore = true)
    @Mapping(target = "normalMchParams", ignore = true)
    PayInterfaceDefine toPayInterfaceDefine(PayInterFaceDefineDTO payInterFaceDefine);

    @Mapping(target = "hasIsvMch", source = "hasSubMch")
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "payWay", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    @Mapping(target = "isvParams", ignore = true)
    @Mapping(target = "isvSubMchParams", ignore = true)
    @Mapping(target = "normalMchParams", ignore = true)
    void copyPayInterfaceDefine(@MappingTarget PayInterfaceDefine payInterfaceDefine, PayInterFaceDefineDTO payInterFaceDefineDTO);

    @Mapping(target = "payWayList", ignore = true)
    @Mapping(target = "hasSubMch", source = "hasIsvMch")
    @Mapping(target = "facilitatorParams", ignore = true)
    @Mapping(target = "subMchParams", ignore = true)
    @Mapping(target = "normalMchParams", ignore = true)
    PayInterfaceDefineVO toPayInterfaceDefineVO(PayInterfaceDefine payInterfaceDefine);

    @Mapping(target = "icon", ignore = true)
    PayInterfaceDefineListVO toPayInterfaceDefineListVO(PayInterfaceDefine payInterfaceDefine);
}
