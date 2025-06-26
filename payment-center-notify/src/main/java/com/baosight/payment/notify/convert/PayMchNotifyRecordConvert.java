package com.baosight.payment.notify.convert;

import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.pojo.vo.OrderNotifyRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/20
 */
@Mapper
public interface PayMchNotifyRecordConvert {
    PayMchNotifyRecordConvert INSTANCE = Mappers.getMapper(PayMchNotifyRecordConvert.class);

    @Mapping(target = "notifyResponseList", ignore = true)
    OrderNotifyRecordVO toOrderNotifyRecordVO(PayMchNotifyRecord payMchNotifyRecord);
}
