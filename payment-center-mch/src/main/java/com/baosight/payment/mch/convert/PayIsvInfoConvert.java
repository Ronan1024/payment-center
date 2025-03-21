//package com.baosight.payment.convert;
//
//import com.baosight.payment.pojo.dto.CreateIsvDTO;
//import com.baosight.payment.pojo.entity.PayIsvInfo;
//import com.baosight.payment.pojo.vo.PayIsvInfoVO;
//import com.baosight.payment.vo.MchInfoVO;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface PayIsvInfoConvert {
//    PayIsvInfoConvert INSTANCE = Mappers.getMapper(PayIsvInfoConvert.class);
//
//    PayIsvInfoVO toPayIsvInfoVO(PayIsvInfo payIsvInfo);
//
//    @Mapping(target = "updateTime", ignore = true)
//    @Mapping(target = "id", source = "isvId")
//    @Mapping(target = "updateByName", ignore = true)
//    @Mapping(target = "updateBy", ignore = true)
//    @Mapping(target = "createTime", ignore = true)
//    @Mapping(target = "createByName", ignore = true)
//    @Mapping(target = "createBy", ignore = true)
//    PayIsvInfo toPayIsvInfo(CreateIsvDTO createIsvDTO);
//
//    MchInfoVO toMchInfoVO(PayIsvInfoVO info);
//}
