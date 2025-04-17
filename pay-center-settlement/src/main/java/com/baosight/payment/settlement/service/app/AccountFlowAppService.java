package com.baosight.payment.settlement.service.app;

import com.baosight.payment.settlement.dto.RegisterSettlementFlowDTO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
public interface AccountFlowAppService {

    /**
     * 注册结算账单流水
     *
     * @param registerSettlementFlow 注册请求体
     */
    Boolean registerSettlementAccountFlow(RegisterSettlementFlowDTO registerSettlementFlow);

}
