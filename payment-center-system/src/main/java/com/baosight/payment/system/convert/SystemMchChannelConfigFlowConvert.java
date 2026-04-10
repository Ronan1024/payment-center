package com.baosight.payment.system.convert;

import com.baosight.payment.system.dao.entity.SystemChannelFlowDefine;
import com.baosight.payment.system.pojo.dto.resp.MchChannelFlowRespDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Mapper
public interface SystemMchChannelConfigFlowConvert {
    SystemMchChannelConfigFlowConvert INSTANCE = Mappers.getMapper(SystemMchChannelConfigFlowConvert.class);


    MchChannelFlowRespDTO toMchChannelFlowRespDTO(SystemChannelFlowDefine systemChannelFlowDefine);
}
