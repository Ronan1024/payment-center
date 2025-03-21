package com.baosight.payment.mch.convert;

import com.baosight.payment.mch.pojo.dto.MchInfoDTO;
import com.baosight.payment.mch.pojo.entity.PayMchInfo;
import com.baosight.payment.mch.pojo.vo.PayMchInfoVO;
import com.baosight.payment.vo.MchInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Mapper
public interface PayMchInfoConvert {
    PayMchInfoConvert INSTANCE = Mappers.getMapper(PayMchInfoConvert.class);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateByName", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createByName", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    PayMchInfo toPayMchInfo(MchInfoDTO mchInfoDTO);

    @Mapping(target = "isvName", ignore = true)
    PayMchInfoVO toPayMchInfoVO(PayMchInfo payMchInfo);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateByName", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createByName", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    void copyPayMchInfo(MchInfoDTO mchInfoDTO, @MappingTarget PayMchInfo payMchInfo);

    MchInfoVO toMchInfoVO(PayMchInfo info);
}
