package com.baosight.payment.check.manager.impl;

import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.check.convert.ChannelBillFileConvert;
import com.baosight.payment.check.error.BillError;
import com.baosight.payment.check.manager.ChannelBillFileManager;
import com.baosight.payment.check.mapper.ChannelBillFileMapper;
import com.baosight.payment.check.pojo.dto.ChannelBillFilePageDTO;
import com.baosight.payment.check.pojo.entity.ChannelBillFile;
import com.baosight.payment.check.pojo.vo.ChannelBillFileVO;
import com.baosight.utils.utils.Assert;
import com.baosight.web.exception.ApiException;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@Manager
@RequiredArgsConstructor
public class ChannelBillFileManagerImpl implements ChannelBillFileManager {
    private final ChannelBillFileMapper channelBillFileMapper;

    /**
     * 获取渠道账单文件列表
     *
     * @param channelBillFilePage 渠道账单文件列表请求信息
     */
    @Override
    public PageResponse<ChannelBillFileVO> channelBillPage(ChannelBillFilePageDTO channelBillFilePage) {
        PageUtil<ChannelBillFileVO> pageUtil = new PageUtil<>(channelBillFilePage);
        return pageUtil.builder(channelBillFileMapper.channelBillPage(pageUtil.Page(), channelBillFilePage)).build();
    }

    /**
     * 获取获取渠道账单文件Id
     *
     * @param id 渠道账单文件id
     */
    @Override
    public ChannelBillFileVO channelBillFileInfo(Long id) {
        ChannelBillFile channelBillFile = channelBillFileMapper.selectById(id);
        Assert.isNull(channelBillFile, ApiException.supplier(BillError.CHANNEL_FILE_NOT_FOUND));
        return ChannelBillFileConvert.INSTANCE.toChannelBillFileVO(channelBillFile);
    }
}
