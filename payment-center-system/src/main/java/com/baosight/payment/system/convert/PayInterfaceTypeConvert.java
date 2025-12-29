package com.baosight.payment.system.convert;


import com.baosight.payment.system.pojo.dto.PayInterfaceTypeDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceType;
import com.baosight.payment.system.pojo.vo.PayInterfaceTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayInterfaceTypeConvert {

    PayInterfaceTypeConvert INSTANCE = Mappers.getMapper(PayInterfaceTypeConvert.class);

    PayInterfaceType toEntity(PayInterfaceTypeDTO payInterfaceTypeDTO);

    PayInterfaceTypeVO toVO(PayInterfaceType payInterfaceType);
}
