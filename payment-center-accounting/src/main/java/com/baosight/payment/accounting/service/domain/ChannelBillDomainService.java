package com.baosight.payment.accounting.service.domain;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.ChannelBillDTO;
import com.baosight.payment.accounting.pojo.entity.ChannelBill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.accounting.pojo.vo.ChannelBillListVO;

/**
* @author longjiangran
* @description 针对表【channel_bill(渠道账单)】的数据库操作Service
* @createDate 2025-04-04 22:01:16
*/
public interface ChannelBillDomainService extends IService<ChannelBill> {

    /**
     * 渠道账单列表
     *
     * @param channelBill 渠道账单列表请求体
     */
    PageResponse<ChannelBillListVO> channelBillPage(ChannelBillDTO channelBill);
}
