package com.baosight.payment.check.manager.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.math.Money;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.check.convert.TradingFlowConvert;
import com.baosight.payment.check.dto.RegisterTradingFlowDTO;
import com.baosight.payment.check.enums.TradingFlowState;
import com.baosight.payment.check.manager.TradingFlowManager;
import com.baosight.payment.check.mapper.TradingFlowMapper;
import com.baosight.payment.check.pojo.entity.TradingFlow;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/15
 */
@Manager
@RequiredArgsConstructor
public class TradingFlowManagerImpl extends ServiceImpl<TradingFlowMapper, TradingFlow> implements TradingFlowManager {
    private final TradingFlowMapper tradingFlowMapper;

    /**
     * 注册交易流水
     *
     * @param registerTradingFlow 注册交易流水信息
     */
    @Override
    public Boolean registerTradingFlow(RegisterTradingFlowDTO registerTradingFlow) {
        Date now = new Date();
        String date = DateUtil.format(now, DatePattern.NORM_DATE_PATTERN);
        TradingFlow tradingFlow = TradingFlowConvert.INSTANCE.toTradingFlow(registerTradingFlow);
        tradingFlow.setDate(date);
        Money money = new Money(registerTradingFlow.getAmount()).divide(100);
        // TODO 渠道成本默认2.6/1000
        BigDecimal channelCost = new BigDecimal(registerTradingFlow.getChannelCost()).divide(new BigDecimal(10000), 6, RoundingMode.HALF_UP);
        Money multiply = money.multiply(channelCost);
        Money canSettleAmount = money.subtract(multiply);
        tradingFlow.setCanSettleAmount(canSettleAmount.multiply(100).getAmount().longValue());
        tradingFlow.setState(TradingFlowState.PENDING.code());
        tradingFlow.setTradingState(registerTradingFlow.getTradingState());
        tradingFlow.setChannelMchNo(registerTradingFlow.getChannelMchNo());
        return tradingFlowMapper.insert(tradingFlow) > 0;
    }
}
