package com.baosight.payment.check.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.check.dto.RegisterTradingFlowDTO;
import com.baosight.payment.check.pojo.entity.TradingFlow;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/15
 */
public interface TradingFlowManager extends IService<TradingFlow> {
    /**
     * 注册交易流水
     *
     * @param registerTradingFlow 注册交易流水信息
     */
    Boolean registerTradingFlow(RegisterTradingFlowDTO registerTradingFlow);
}
