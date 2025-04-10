package com.baosight.payment.accounting.service.app;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.ChannelBillDTO;
import com.baosight.payment.accounting.pojo.vo.ChannelBillListVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
public interface ChannelBillAppService {
    /**
     * 渠道账单列表
     * @param channelBill 渠道账单列表请求体
     */
    PageResponse<ChannelBillListVO> channelBillPage(ChannelBillDTO channelBill);
}
