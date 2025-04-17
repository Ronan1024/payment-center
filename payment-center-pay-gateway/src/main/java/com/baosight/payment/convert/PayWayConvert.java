package com.baosight.payment.convert;

import com.baosight.payment.model.payway.PayWayModel;
import com.baosight.payment.vo.ApiPayWayVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWayConvert {

    PayWayConvert INSTANCE = Mappers.getMapper(PayWayConvert.class);

    @Mapping(target = "payName", source = "name")
    PayWayModel toPayWayModel(ApiPayWayVO apiPayWayVO);
}
