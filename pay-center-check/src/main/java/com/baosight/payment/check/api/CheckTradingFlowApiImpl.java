package com.baosight.payment.check.api;

import com.baosight.payment.check.dto.RegisterTradingFlowDTO;
import com.baosight.payment.check.manager.TradingFlowManager;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/15
 */
@Component
@RequiredArgsConstructor
public class CheckTradingFlowApiImpl implements CheckTradingFlowApi {
    private final TradingFlowManager tradingFlowManager;


    /**
     * 注册交易流水
     *
     * @param registerTradingFlow 注册交易流水信息
     */
    @Override
    public Boolean registerTradingFlow(RegisterTradingFlowDTO registerTradingFlow) {
        Assert.isTrue(ObjectUtils.isEmpty(registerTradingFlow.getChannelCost()) || registerTradingFlow.getChannelCost() < 0, "渠道成本不能为空或不能小于0");
        return tradingFlowManager.registerTradingFlow(registerTradingFlow);
    }
}
