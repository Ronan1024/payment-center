package com.baosight.payment.order.manager.impl;

import com.baosight.payment.order.api.dto.CreateRefundOrderDTO;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;
import com.baosight.payment.order.pojo.entity.PayRefundOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Mapper
public interface PayReFundConvert {
    PayReFundConvert INSTANCE = Mappers.getMapper(PayReFundConvert.class);

    @Mapping(target = "originMchPayOrderNo", source = "payMchOrgOrderNo")
    @Mapping(target = "chanelResult", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "successTime", ignore = true)
    @Mapping(target = "refundNo", ignore = true)
    @Mapping(target = "id", ignore = true)
    PayRefundOrder toPayRefundOrder(CreateRefundOrderDTO refundOrder);

    PayRefundOrderVO toPayRefundOrderVO(PayRefundOrder payRefundOrder);
}
