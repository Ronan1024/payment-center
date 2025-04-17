package com.baosight.payment.settlement.service.app;

import com.baosight.payment.settlement.dto.CreateSettlementRequestDTO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
public interface SettlementRequestAppService {

    /**
     * 创建结算处理单
     * @param createSettlementRequest 创建请求信息
     */
    Boolean createSettlementRequest(CreateSettlementRequestDTO createSettlementRequest);
}
