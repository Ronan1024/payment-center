package com.baosight.payment.settlement.api;

import com.baosight.payment.settlement.dto.CreateSettlementRequestDTO;
import com.baosight.payment.settlement.manager.SettlementRequestManager;
import com.baosight.payment.settlement.service.app.SettlementRequestAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 结算受理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@Component
@RequiredArgsConstructor
public class SettlementRequestApiImpl implements SettlementRequestApi {
    private final SettlementRequestAppService settlementRequestAppService;
    private final SettlementRequestManager settlementRequestManager;


    /**
     * 创建结算请求
     */
    @Override
    public Boolean createSettlementRequest(CreateSettlementRequestDTO createSettlementRequest) {
        return settlementRequestManager.createSettlementRequest(createSettlementRequest);


    }
}
