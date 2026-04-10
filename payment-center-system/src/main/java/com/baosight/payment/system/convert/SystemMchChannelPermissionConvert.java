package com.baosight.payment.system.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/8
 */
@Mapper
public interface SystemMchChannelPermissionConvert {
    SystemMchChannelPermissionConvert INSTANCE = Mappers.getMapper(SystemMchChannelPermissionConvert.class);

}
