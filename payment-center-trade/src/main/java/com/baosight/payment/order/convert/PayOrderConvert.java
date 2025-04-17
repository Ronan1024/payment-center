package com.baosight.payment.order.convert;

import com.baosight.payment.check.dto.RegisterTradingFlowDTO;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.pojo.vo.PayOrderInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    @Mapping(target = "tradingState", ignore = true)
    @Mapping(target = "tradingTime", source = "successTime")
    @Mapping(target = "originOrderId", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "channelOrderId", source = "channelOrderNo")
    @Mapping(target = "channelId", ignore = true)
    @Mapping(target = "channelCost", expression = "java(26L)")
    @Mapping(target = "channelCode", source = "ifCode")
    @Mapping(target = "amount", source = "payAmount")
    RegisterTradingFlowDTO toRegisterTradingFlowDTO(PayOrder payOrder);
}
