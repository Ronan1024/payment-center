package com.baosight.payment.check.service.impl.domain;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.payment.check.manager.ChannelBillFileManager;
import com.baosight.payment.check.mapper.ChannelBillFileMapper;
import com.baosight.payment.check.pojo.dto.ChannelBillFilePageDTO;
import com.baosight.payment.check.pojo.entity.ChannelBillFile;
import com.baosight.payment.check.pojo.vo.ChannelBillFileVO;
import com.baosight.payment.check.service.domain.ChannelBillFileDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author longjiangran
 * @description 针对表【channel_bill_file(渠道账单文件)】的数据库操作Service实现
 * @createDate 2025-04-07 14:36:36
 */
@Service
@RequiredArgsConstructor
public class ChannelBillFileDomainServiceImpl extends ServiceImpl<ChannelBillFileMapper, ChannelBillFile> implements ChannelBillFileDomainService {

    public final ChannelBillFileManager channelBillFileManager;


    /**
     * 获取渠道账单文件列表
     *
     * @param channelBillFilePage 渠道账单文件列表请求信息
     */
    @Override
    public PageResponse<ChannelBillFileVO> channelBillPage(ChannelBillFilePageDTO channelBillFilePage) {
        return channelBillFileManager.channelBillPage(channelBillFilePage);
    }

    /**
     * 获取获取渠道账单文件Id
     *
     * @param id 渠道账单文件id
     */
    @Override
    public ChannelBillFileVO channelBillFileInfo(Long id) {
        return channelBillFileManager.channelBillFileInfo(id);
    }
}




