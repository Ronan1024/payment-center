package com.baosight.payment.settlement.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.settlement.dto.CreateSettlementRequestDTO;
import com.baosight.payment.settlement.manager.SettlementRequestManager;
import com.baosight.payment.settlement.mapper.SettlementRequestMapper;
import com.baosight.payment.settlement.pojo.entity.SettlementRequest;
import com.baosight.utils.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

/**
 * @author longjiangran
 * @description 针对表【settlement_request(结算受理单)】的数据库操作Service实现
 * @createDate 2025-04-16 10:44:26
 */
@Manager
@RequiredArgsConstructor
public class SettlementRequestManagerImpl extends ServiceImpl<SettlementRequestMapper, SettlementRequest> implements SettlementRequestManager {

    private final SettlementRequestMapper settlementRequestMapper;

    /**
     * 创建结算受理
     *
     * @param createSettlementRequest 结算受理请求
     */
    @Override
    public Boolean createSettlementRequest(CreateSettlementRequestDTO createSettlementRequest) {
        SettlementRequest settlementRequest = settlementRequestMapper.selectOne(new LambdaQueryWrapper<SettlementRequest>()
                .eq(SettlementRequest::getOrderId, createSettlementRequest.getOrderId()));
        if (!ObjectUtils.isEmpty(settlementRequest)) {
            return Boolean.TRUE;
        }

        settlementRequest = new SettlementRequest();
        settlementRequest.setAmount(createSettlementRequest.getAmount().getAmount());
        settlementRequest.setCreateTime(createSettlementRequest.getFirmTime());
//        settlementRequest.setSettleAmount(createSettlementRequest.getSettleAmount().getAmount());
        settlementRequest.setType(createSettlementRequest.getType());
        // TODO 默认受理成功
        settlementRequest.setState(1);
        settlementRequest.setOrderId(createSettlementRequest.getOrderId());
        return settlementRequestMapper.insert(settlementRequest) > 0;
    }
}




