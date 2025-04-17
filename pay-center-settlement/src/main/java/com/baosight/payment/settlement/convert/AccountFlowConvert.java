package com.baosight.payment.settlement.convert;

import com.baosight.payment.settlement.dto.RegisterSettlementFlowDTO;
import com.baosight.payment.settlement.pojo.entity.AccountFlow;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@Mapper
public interface AccountFlowConvert {
    AccountFlowConvert INSTANCE = Mappers.getMapper(AccountFlowConvert.class);

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "channelFee", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "channelSettlementAmount", ignore = true)
    AccountFlow toAccountFlow(RegisterSettlementFlowDTO registerSettlementFlow);

}
