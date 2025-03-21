package com.baosight.payment.isv.convert;

import com.baosight.payment.isv.pojo.dto.CreateIsvDTO;
import com.baosight.payment.isv.pojo.entity.PayIsvInfo;
import com.baosight.payment.isv.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.isv.pojo.vo.PayIsvPageVO;
import com.baosight.payment.isv.vo.IsvInfoVO;
import com.baosight.payment.vo.MchInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayIsvInfoConvert {
    PayIsvInfoConvert INSTANCE = Mappers.getMapper(PayIsvInfoConvert.class);

    PayIsvInfoVO toPayIsvInfoVO(PayIsvInfo payIsvInfo);

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "id", source = "isvId")
    @Mapping(target = "updateByName", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createByName", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    PayIsvInfo toPayIsvInfo(CreateIsvDTO createIsvDTO);

    MchInfoVO toMchInfoVO(PayIsvInfoVO info);


    IsvInfoVO toIsvInfoVO(PayIsvInfo payIsvInfo);

    PayIsvPageVO toPayIsvPageVO(PayIsvInfo payIsvInfo);
}
