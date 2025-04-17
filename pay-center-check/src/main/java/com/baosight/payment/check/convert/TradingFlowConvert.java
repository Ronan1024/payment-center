package com.baosight.payment.check.convert;

import com.baosight.payment.check.dto.RegisterTradingFlowDTO;
import com.baosight.payment.check.pojo.entity.TradingFlow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/15
 */
@Mapper
public interface TradingFlowConvert {
    TradingFlowConvert INSTANCE = Mappers.getMapper(TradingFlowConvert.class);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "state", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "canSettleAmount", ignore = true)
    TradingFlow toTradingFlow(RegisterTradingFlowDTO registerTradingFlow);
}
