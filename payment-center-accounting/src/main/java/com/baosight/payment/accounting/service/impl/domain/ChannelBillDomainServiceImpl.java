package com.baosight.payment.accounting.service.impl.domain;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.manager.ChannelBillManager;
import com.baosight.payment.accounting.pojo.dto.ChannelBillDTO;
import com.baosight.payment.accounting.pojo.entity.ChannelBill;
import com.baosight.payment.accounting.pojo.vo.ChannelBillListVO;
import com.baosight.payment.accounting.service.domain.ChannelBillDomainService;
import com.baosight.payment.accounting.mapper.ChannelBillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author longjiangran
* @description 针对表【channel_bill(渠道账单)】的数据库操作Service实现
* @createDate 2025-04-04 22:01:16
*/
@Service
@RequiredArgsConstructor
public class ChannelBillDomainServiceImpl extends ServiceImpl<ChannelBillMapper, ChannelBill> implements ChannelBillDomainService {

    private final ChannelBillManager channelBillManager;

    /**
     * 渠道账单列表
     *
     * @param channelBill 渠道账单列表请求体
     */
    @Override
    public PageResponse<ChannelBillListVO> channelBillPage(ChannelBillDTO channelBill) {
        return channelBillManager.channelBillPage(channelBill);
    }
}




