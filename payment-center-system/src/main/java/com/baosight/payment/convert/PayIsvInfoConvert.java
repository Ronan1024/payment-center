package com.baosight.payment.convert;

import com.baosight.payment.pojo.entity.PayIsvInfo;
import com.baosight.payment.pojo.vo.PayIsvInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayIsvInfoConvert {
    PayIsvInfoConvert INSTANCE = Mappers.getMapper(PayIsvInfoConvert.class);

    PayIsvInfoVO toPayIsvInfoVO(PayIsvInfo payIsvInfo);
}
