package com.baosight.payment.accounting.manager;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.ChannelBillDTO;
import com.baosight.payment.accounting.pojo.entity.ChannelBill;
import com.baosight.payment.accounting.pojo.vo.ChannelBillListVO;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
public interface ChannelBillManager {
    /**
     * 渠道账单列表
     *
     * @param channelBill 渠道账单列表请求体
     */
    PageResponse<ChannelBillListVO> channelBillPage(ChannelBillDTO channelBill);

    /**
     * 获取渠道分组
     * @param state 状态
     */
    List<ChannelBill> getChannelGroup(Integer state);
}
