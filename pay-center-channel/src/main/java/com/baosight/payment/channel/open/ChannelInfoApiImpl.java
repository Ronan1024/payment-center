package com.baosight.payment.channel.open;

import com.baosight.payment.channel.api.ChannelInfoApi;
import com.baosight.payment.channel.convert.PayingChannelInfoConvert;
import com.baosight.payment.channel.dao.entity.PayingChannelInfo;
import com.baosight.payment.channel.dao.manager.PayingChannelInfoManager;
import com.baosight.payment.channel.dto.resp.ChannelInfoRespDTO;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.baosight.payment.channel.error.ChannelError.CHANNEL_INFO_NOT_EXIST;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Component
@RequiredArgsConstructor
public class ChannelInfoApiImpl implements ChannelInfoApi {

    private final PayingChannelInfoManager payingChannelInfoManager;
    /**
     * 获取渠道信息
     *
     * @param code 渠道编号
     */
    @Override
    public ChannelInfoRespDTO info(String code) {
        PayingChannelInfo payingChannelInfo = payingChannelInfoManager.infoByCode(code);
        Assert.isNull(payingChannelInfo, ApiException.supplier(CHANNEL_INFO_NOT_EXIST));
        return PayingChannelInfoConvert.INSTANCE.toChannelInfoRespDTO(payingChannelInfo);
    }
}
