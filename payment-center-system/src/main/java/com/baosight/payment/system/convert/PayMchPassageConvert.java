package com.baosight.payment.system.convert;

import com.baosight.payment.system.pojo.entity.PayMchPassage;
import com.baosight.payment.vo.MchAppPassageVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/27
 */
@Mapper
public interface PayMchPassageConvert {
    PayMchPassageConvert INSTANCE = Mappers.getMapper(PayMchPassageConvert.class);

    MchAppPassageVO toMchAppPassageVO(PayMchPassage payMchPassage);
}
