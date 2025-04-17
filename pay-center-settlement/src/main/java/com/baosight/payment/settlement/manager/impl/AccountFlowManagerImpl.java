package com.baosight.payment.settlement.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.settlement.convert.AccountFlowConvert;
import com.baosight.payment.settlement.dto.RegisterSettlementFlowDTO;
import com.baosight.payment.settlement.manager.AccountFlowManager;
import com.baosight.payment.settlement.mapper.AccountFlowMapper;
import com.baosight.payment.settlement.mapper.SettlementRequestMapper;
import com.baosight.payment.settlement.pojo.entity.AccountFlow;
import lombok.RequiredArgsConstructor;

/**
 * @author longjiangran
 * @description 针对表【account_flow(账单流水)】的数据库操作Service实现
 * @createDate 2025-04-16 10:44:26
 */

@Manager
@RequiredArgsConstructor
public class AccountFlowManagerImpl extends ServiceImpl<AccountFlowMapper, AccountFlow> implements AccountFlowManager {

    private final AccountFlowMapper accountFlowMapper;
    private final SettlementRequestMapper settlementRequestMapper;
    /**
     * 注册结算账单流水
     *
     * @param registerSettlementFlow 注册请求体
     */
    @Override
    public Boolean registerSettlementAccountFlow(RegisterSettlementFlowDTO registerSettlementFlow) {
        AccountFlow accountFlow = AccountFlowConvert.INSTANCE.toAccountFlow(registerSettlementFlow);
//        accountFlow.setAmount(registerSettlementFlow.getAmount().getAmount());
//        accountFlow.setChannelFee(registerSettlementFlow.getChannelFee().getAmount());
//        accountFlow.setChannelSettlementAmount(registerSettlementFlow.getChannelSettlementAmount().getAmount());
//        if (!ObjectUtils.isEmpty(registerSettlementFlow.getOriginalOrderId())){
//             如果存在原始订单号，则查询原始订单号对应的结算受理单
//            settlementRequestMapper.selectOne(new LambdaQueryWrapper<SettlementRequest>()
//                    .eq(SettlementRequest::getMchId, registerSettlementFlow.getMchId())
//                    .eq(SettlementRequest::getTradingId, registerSettlementFlow.getOriginalOrderId())
//            )
//        }
        return null;
    }
}




