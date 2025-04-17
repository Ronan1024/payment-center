package com.baosight.payment.settlement.service.impl.app;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baosight.payment.annotation.ApplicationService;
import com.baosight.payment.settlement.dto.CreateSettlementRequestDTO;
import com.baosight.payment.settlement.manager.SettlementRequestManager;
import com.baosight.payment.settlement.service.app.SettlementRequestAppService;
import com.baosight.payment.settlement.service.domain.SettlementPeriodDomainService;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/16
 */
@ApplicationService
@RequiredArgsConstructor
public class SettlementRequestAppServiceImpl implements SettlementRequestAppService {
    private final SettlementRequestManager settlementRequestManager;
    private final SettlementPeriodDomainService settlementPeriodDomainService;

    /**
     * 创建结算处理单
     *
     * @param createSettlementRequest 创建请求信息
     */
    @Override
    public Boolean createSettlementRequest(CreateSettlementRequestDTO createSettlementRequest) {
        // TODO 默认两天后处理
        DateTime dateTime = DateUtil.offsetDay(createSettlementRequest.getFirmTime(), 2);

        // 注册账期

        return null;
    }
}
