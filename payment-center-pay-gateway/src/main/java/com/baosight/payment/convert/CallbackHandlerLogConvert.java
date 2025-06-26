package com.baosight.payment.convert;

import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baosight.payment.pojo.vo.CallbackHandlerLogDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/22
 */
@Mapper
public interface CallbackHandlerLogConvert {

    CallbackHandlerLogConvert INSTANCE = Mappers.getMapper(CallbackHandlerLogConvert.class);


    CallbackHandlerLogDetailVO toCallbackHandlerLogDetailVO(CallbackHandlerLog callbackHandlerLog);
}
