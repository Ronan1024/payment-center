package com.baosight.payment.settlement.convert;

import com.baosight.payment.settlement.pojo.entity.MchAccountRecord;
import com.baosight.payment.settlement.vo.MchAccountRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/22
 */
@Mapper
public interface MchAccountRecordConvert {
    MchAccountRecordConvert INSTANCE = Mappers.getMapper(MchAccountRecordConvert.class);

    MchAccountRecordVO toMchAccountRecordVO(MchAccountRecord mchAccountRecord);
}
