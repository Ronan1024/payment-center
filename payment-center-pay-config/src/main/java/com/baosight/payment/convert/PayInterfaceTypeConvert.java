package com.baosight.payment.convert;


import com.baosight.payment.pojo.dto.PayInterfaceTypeDTO;
import com.baosight.payment.pojo.entity.PayInterfaceType;
import com.baosight.payment.pojo.vo.PayInterfaceTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayInterfaceTypeConvert {

    PayInterfaceTypeConvert INSTANCE = Mappers.getMapper(PayInterfaceTypeConvert.class);

    PayInterfaceType toEntity(PayInterfaceTypeDTO payInterfaceTypeDTO);

    PayInterfaceTypeVO toVO(PayInterfaceType payInterfaceType);
}
