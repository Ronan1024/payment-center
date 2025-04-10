package com.baosight.payment.accounting.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.payment.accounting.manager.ChannelBillManager;
import com.baosight.payment.accounting.mapper.ChannelBillMapper;
import com.baosight.payment.accounting.pojo.dto.ChannelBillDTO;
import com.baosight.payment.accounting.pojo.entity.ChannelBill;
import com.baosight.payment.accounting.pojo.vo.ChannelBillListVO;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.utils.enums.IBaseEnum;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */

@Manager
@RequiredArgsConstructor
public class ChannelBillManagerImpl implements ChannelBillManager {

    private final ChannelBillMapper channelBillMapper;

    /**
     * 渠道账单列表
     *
     * @param channelBill 渠道账单列表请求体
     */
    @Override
    public PageResponse<ChannelBillListVO> channelBillPage(ChannelBillDTO channelBill) {
        PageUtil<ChannelBillListVO> pageUtil = new PageUtil<>(channelBill);
        PageResponse<ChannelBillListVO> build = pageUtil.builder(channelBillMapper.channelBillPage(pageUtil.Page(), channelBill)).build();
        List<ChannelBillListVO> list = build.getList();
        list.forEach(e -> {
            PayInterfaceCode payInterfaceCode = IBaseEnum.getByCode(PayInterfaceCode.class, e.getChannelCode());
            e.setChannelName(payInterfaceCode.getMsg());
        });

        return build;
    }

    /**
     * 获取渠道分组
     *
     * @param state 状态
     */
    @Override
    public List<ChannelBill> getChannelGroup(Integer state) {
        return channelBillMapper.selectList(new LambdaQueryWrapper<ChannelBill>()
                .select(ChannelBill::getBillDate, ChannelBill::getChannelMchNo, ChannelBill::getChannelCode, ChannelBill::getTradeType, ChannelBill::getChannelId)
                .eq(ChannelBill::getBillState, state)
                .groupBy(ChannelBill::getBillDate, ChannelBill::getChannelMchNo, ChannelBill::getChannelCode, ChannelBill::getTradeType, ChannelBill::getChannelId));
    }
}
