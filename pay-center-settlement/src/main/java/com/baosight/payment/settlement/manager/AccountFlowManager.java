package com.baosight.payment.settlement.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.settlement.dto.RegisterSettlementFlowDTO;
import com.baosight.payment.settlement.pojo.entity.AccountFlow;

/**
 * @author longjiangran
 * @description 针对表【account_flow(账单流水)】的数据库操作Service
 * @createDate 2025-04-16 10:44:26
 */
public interface AccountFlowManager extends IService<AccountFlow> {

    /**
     * 注册结算账单流水
     *
     * @param registerSettlementFlow 注册请求体
     */
    Boolean registerSettlementAccountFlow(RegisterSettlementFlowDTO registerSettlementFlow);
}
