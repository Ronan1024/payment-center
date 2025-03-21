package com.baosight.payment.order.convert;

import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.order.pojo.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Mapper
public interface OrderConvert {
    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    OrderVO toOrderVO(PayOrder byId);
}
