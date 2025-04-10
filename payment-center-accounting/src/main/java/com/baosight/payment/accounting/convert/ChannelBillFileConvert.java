package com.baosight.payment.accounting.convert;

import com.baosight.payment.accounting.pojo.entity.ChannelBillFile;
import com.baosight.payment.accounting.pojo.vo.ChannelBillFileVO;
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
