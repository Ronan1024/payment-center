package com.baosight.payment.settlement.api;

import com.baosight.payment.settlement.dto.RegisterSettlementFlowDTO;

/**
 * 结算受理账单流水处理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
public interface SettlementRequestFlowApi {

    /**
     * 注册
     *
     * @param registerSettlementFlow 注册请求体
     */
    Boolean registerSettlementAccountFlow(RegisterSettlementFlowDTO registerSettlementFlow);
}
