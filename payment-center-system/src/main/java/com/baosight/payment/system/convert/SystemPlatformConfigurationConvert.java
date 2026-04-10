package com.baosight.payment.system.convert;

import com.baosight.payment.dao.resp.AllInRespDTO;
import com.baosight.payment.system.dao.entity.SystemPlatformConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
@Mapper
public interface SystemPlatformConfigurationConvert {
    SystemPlatformConfigurationConvert INSTANCE = Mappers.getMapper(SystemPlatformConfigurationConvert.class);


    AllInRespDTO toAllInRespDTO(SystemPlatformConfiguration.AllIn parse);
}
