package com.baosight.payment.convert;

import com.baosight.payment.pojo.entity.PayWay;
import com.baosight.payment.pojo.vo.PayWayPageVO;
import com.baosight.payment.pojo.vo.PayWayVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayWayConvert {

    PayWayConvert INSTANCE = Mappers.getMapper(PayWayConvert.class);

    PayWayVO toPayWayVO(PayWay payWay);

    PayWayPageVO toPayWayPageVO(PayWay payWay);
}
