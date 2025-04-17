package com.baosight.payment.check.manager;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.check.pojo.dto.ChannelBillFilePageDTO;
import com.baosight.payment.check.pojo.vo.ChannelBillFileVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
public interface ChannelBillFileManager {
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
