package com.baosight.payment.settlement.dao;

import lombok.Data;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@Data
public class SettlementFlowDAO {
    private List<Long> orderId;
}
