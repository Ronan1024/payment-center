package com.baosight.payment.system.convert;

import com.baosight.payment.system.pojo.dto.PayInterFaceDefineDTO;
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

    @Mapping(target = "id", ignore = true)
    PayInterfaceDefine toPayInterfaceDefine(PayInterFaceDefineDTO payInterFaceDefine);

    @Mapping(target = "id", ignore = true)
    void copyPayInterfaceDefine(@MappingTarget PayInterfaceDefine payInterfaceDefine, PayInterFaceDefineDTO payInterFaceDefineDTO);

    PayInterfaceDefineVO toPayInterfaceDefineVO(PayInterfaceDefine payInterfaceDefine);

    PayInterfaceDefineListVO toPayInterfaceDefineListVO(PayInterfaceDefine payInterfaceDefine);
}
