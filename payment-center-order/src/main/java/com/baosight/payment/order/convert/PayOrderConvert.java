package com.baosight.payment.order.convert;

import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.pojo.vo.PayOrderInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@Mapper
public interface PayOrderConvert {
    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    PayOrderInfoVO toPayOrderInfoVO(PayOrder payOrder);
}
