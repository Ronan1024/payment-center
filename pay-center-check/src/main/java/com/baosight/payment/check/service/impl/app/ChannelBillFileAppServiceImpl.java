package com.baosight.payment.check.service.impl.app;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.check.pojo.dto.ChannelBillFilePageDTO;
import com.baosight.payment.check.pojo.vo.ChannelBillFileVO;
import com.baosight.payment.check.service.app.ChannelBillFileAppService;
import com.baosight.payment.check.service.domain.ChannelBillFileDomainService;
import com.baosight.payment.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@ApplicationService
@RequiredArgsConstructor
public class ChannelBillFileAppServiceImpl implements ChannelBillFileAppService {

    private final ChannelBillFileDomainService channelBillFileDomainService;


    /**
     * 获取渠道账单文件列表
     *
     * @param channelBillFilePage 渠道账单文件列表请求信息
     */
    @Override
    public PageResponse<ChannelBillFileVO> channelBillPage(ChannelBillFilePageDTO channelBillFilePage) {
        return channelBillFileDomainService.channelBillPage(channelBillFilePage);
    }

    /**
     * 获取获取渠道账单文件Id
     *
     * @param id 渠道账单文件id
     */
    @Override
    public ChannelBillFileVO channelBillFileInfo(Long id) {
        return channelBillFileDomainService.channelBillFileInfo(id);
    }
}
