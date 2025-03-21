package com.baosight.payment.system.convert;

import com.baosight.payment.system.pojo.entity.PayMchApp;
import com.baosight.payment.system.pojo.vo.MchAppListVO;
import com.baosight.payment.system.pojo.vo.MchPayAppInfoVO;
import com.baosight.payment.vo.MchAppInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayMchAppConvert {
    PayMchAppConvert INSTANCE = Mappers.getMapper(PayMchAppConvert.class);

    MchPayAppInfoVO toMchPayAppInfoVO(PayMchApp payMchApp);

    MchAppListVO toMchAppListVO(PayMchApp payMchApp);

    MchAppInfoVO toMchAppInfoVO(PayMchApp payMchApp);
}
