package com.baosight.payment.settlement.api;

import com.baosight.payment.settlement.dto.RegisterSettlementFlowDTO;
import com.baosight.payment.settlement.manager.AccountFlowManager;
import com.baosight.payment.settlement.service.app.AccountFlowAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 结算受理账单流水处理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@Component
@RequiredArgsConstructor
public class SettlementRequestFlowApiImpl implements SettlementRequestFlowApi {
    private final AccountFlowManager accountFlowManager;
    private final AccountFlowAppService accountFlowAppService;


    /**
     * 注册结算账单流水
     *
     * @param registerSettlementFlow 注册请求体
     */
    @Override
    public Boolean registerSettlementAccountFlow(RegisterSettlementFlowDTO registerSettlementFlow) {
        return accountFlowAppService.registerSettlementAccountFlow(registerSettlementFlow);
    }
}
