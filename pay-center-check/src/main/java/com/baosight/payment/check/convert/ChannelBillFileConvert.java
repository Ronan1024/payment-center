package com.baosight.payment.check.convert;

import com.baosight.payment.check.pojo.entity.ChannelBillFile;
import com.baosight.payment.check.pojo.vo.ChannelBillFileVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@Mapper
public interface ChannelBillFileConvert {
    ChannelBillFileConvert INSTANCE = Mappers.getMapper(ChannelBillFileConvert.class);

    ChannelBillFileVO toChannelBillFileVO(ChannelBillFile channelBillFile);
}
