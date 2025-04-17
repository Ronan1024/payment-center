package com.baosight.payment.settlement.api;

import com.baosight.payment.settlement.dto.CreateSettlementRequestDTO;

/**
 * 结算受理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
public interface SettlementRequestApi {

    /**
     * 创建结算请求
     */
    Boolean createSettlementRequest(CreateSettlementRequestDTO createSettlementRequest);
}
