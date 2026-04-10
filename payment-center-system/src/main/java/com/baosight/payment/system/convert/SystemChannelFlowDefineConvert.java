package com.baosight.payment.system.convert;

import com.baosight.payment.system.dao.entity.SystemChannelFlowDefine;
import com.baosight.payment.system.pojo.dto.req.SavePaymentChannelFlowReqDTO;
import com.baosight.payment.system.pojo.dto.resp.MchChannelFlowRespDTO;
import com.baosight.payment.system.pojo.dto.resp.PaymentChannelFlowRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 支付渠道流程转换器
 *
 * @author L.J.Ran
 */
@Mapper
public interface SystemChannelFlowDefineConvert {

    SystemChannelFlowDefineConvert INSTANCE = Mappers.getMapper(SystemChannelFlowDefineConvert.class);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "channelId", ignore = true)
    SystemChannelFlowDefine toEntity(SavePaymentChannelFlowReqDTO savePaymentChannelFlowDTO);


    PaymentChannelFlowRespDTO toPaymentChannelFlowRespDTO(SystemChannelFlowDefine paymentChannelFlow);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "name", source = "flowName")
    @Mapping(target = "index", source = "stepOrder")
    @Mapping(target = "handleTime", ignore = true)
    @Mapping(target = "handleResult", ignore = true)
    MchChannelFlowRespDTO toMchChannelFlowRespDTO(SystemChannelFlowDefine systemChannelFlowDefine);
}
