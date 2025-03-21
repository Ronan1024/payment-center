package com.baosight.payment.system.convert;

import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.system.pojo.vo.PayWayListVO;
import com.baosight.payment.system.pojo.vo.PayWayPageVO;
import com.baosight.payment.system.pojo.vo.PayWayVO;
import com.baosight.payment.vo.ApiPayWayVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWayConvert {

    PayWayConvert INSTANCE = Mappers.getMapper(PayWayConvert.class);

    PayWayVO toPayWayVO(PayWay payWay);

    @Mapping(target = "name", source = "payName")
    @Mapping(target = "icon", ignore = true)
    ApiPayWayVO toApiPayWayVO(PayWay payWay);

    PayWayPageVO toPayWayPageVO(PayWay payWay);

    PayWayListVO toPayWayListVO(PayWay payWay);
}
