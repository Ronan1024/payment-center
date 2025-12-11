package com.baosight.payment.check.service.domain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.check.pojo.dto.ChannelBillFilePageDTO;
import com.baosight.payment.check.pojo.entity.ChannelBillFile;
import com.baosight.payment.check.pojo.vo.ChannelBillFileVO;

/**
* @author longjiangran
* @description 针对表【channel_bill_file(渠道账单文件)】的数据库操作Service
* @createDate 2025-04-07 14:36:36
*/
public interface ChannelBillFileDomainService extends IService<ChannelBillFile> {

    /**
     * 获取渠道账单文件列表
     *
     * @param channelBillFilePage 渠道账单文件列表请求信息
     */
    PageResponse<ChannelBillFileVO> channelBillPage(ChannelBillFilePageDTO channelBillFilePage);
    /**
     * 获取获取渠道账单文件Id
     *
     * @param id 渠道账单文件id
     */
    ChannelBillFileVO channelBillFileInfo(Long id);
}
