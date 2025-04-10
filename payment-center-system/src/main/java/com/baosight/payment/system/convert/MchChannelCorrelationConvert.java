package com.baosight.payment.system.convert;

import com.baosight.payment.system.pojo.entity.MchChannelCorrelation;
import com.baosight.payment.vo.MchChannelCorrelationVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Mapper
public interface MchChannelCorrelationConvert {
    MchChannelCorrelationConvert INSTANCE = Mappers.getMapper(MchChannelCorrelationConvert.class);

    MchChannelCorrelationVO toMchChannelCorrelationVO(MchChannelCorrelation mchChannelCorrelation);
}
