package com.baosight.payment.channel.open;

import com.baosight.payment.channel.api.ChannelInfoApi;
import com.baosight.payment.channel.convert.ChannelInfoConvert;
import com.baosight.payment.channel.dao.entity.ChannelInfo;
import com.baosight.payment.channel.dao.manager.ChannelInfoManager;
import com.baosight.payment.channel.dto.resp.ChannelInfoRespDTO;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    private final ChannelInfoManager channelInfoManager;
    /**
     * 获取渠道信息
     *
     * @param code 渠道编号
     */
    @Override
    public ChannelInfoRespDTO info(String code) {
        ChannelInfo channelInfo = channelInfoManager.infoByCode(code);
        Assert.isNull(channelInfo, ApiException.supplier(CHANNEL_INFO_NOT_EXIST));
        return ChannelInfoConvert.INSTANCE.toChannelInfoRespDTO(channelInfo);
    }

    /**
     * 获取渠道信息
     *
     * @param codes 渠道编号
     */
    @Override
    public List<ChannelInfoRespDTO> info(List<String> codes) {
        List<ChannelInfo> channelInfos = channelInfoManager.infoByCode(codes);
        Assert.isFalse(codes.size()== channelInfos.size(), ApiException.supplier(CHANNEL_INFO_NOT_EXIST));
        return channelInfos.stream().map(ChannelInfoConvert.INSTANCE::toChannelInfoRespDTO).toList();
    }
}
