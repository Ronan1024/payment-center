package com.baosight.payment.check.api;

import com.baosight.payment.check.dto.RegisterTradingFlowDTO;

/**
 * 对账交易流水
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/15
 */
public interface CheckTradingFlowApi {

    /**
     * 注册交易流水
     * @param registerTradingFlow 注册交易流水信息
     */
    Boolean registerTradingFlow(RegisterTradingFlowDTO registerTradingFlow);
}
