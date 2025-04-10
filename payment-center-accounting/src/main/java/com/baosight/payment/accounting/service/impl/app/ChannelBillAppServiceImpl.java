package com.baosight.payment.accounting.service.impl.app;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.ChannelBillDTO;
import com.baosight.payment.accounting.pojo.vo.ChannelBillListVO;
import com.baosight.payment.accounting.service.app.ChannelBillAppService;
import com.baosight.payment.accounting.service.domain.ChannelBillDomainService;
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
public class ChannelBillAppServiceImpl implements ChannelBillAppService {
    private final ChannelBillDomainService channelBillDomainService;

    /**
     * 渠道账单列表
     *
     * @param channelBill 渠道账单列表请求体
     */
    @Override
    public PageResponse<ChannelBillListVO> channelBillPage(ChannelBillDTO channelBill) {
        return channelBillDomainService.channelBillPage(channelBill);
    }
}
