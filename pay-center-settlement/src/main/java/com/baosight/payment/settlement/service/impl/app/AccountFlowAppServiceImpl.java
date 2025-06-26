package com.baosight.payment.settlement.service.impl.app;

import com.baosight.payment.annotation.ApplicationService;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.settlement.dto.RegisterSettlementFlowDTO;
import com.baosight.payment.settlement.manager.SettlementRequestManager;
import com.baosight.payment.settlement.service.app.AccountFlowAppService;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@ApplicationService
@RequiredArgsConstructor
public class AccountFlowAppServiceImpl implements AccountFlowAppService {
    private final OrderApi orderApi;
    private final SettlementRequestManager settlementRequestManager;

    /**
     * 注册结算账单流水
     *
     * @param registerSettlementFlow 注册请求体
     */
    @Override
    public Boolean registerSettlementAccountFlow(RegisterSettlementFlowDTO registerSettlementFlow) {
        return null;
    }
}
