package com.baosight.payment.settlement.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.settlement.manager.SettlementFlowManager;
import com.baosight.payment.settlement.mapper.SettlementFlowMapper;
import com.baosight.payment.settlement.pojo.entity.SettlementFlow;

/**
* @author longjiangran
* @description 针对表【settlement_flow(结算流水单表)】的数据库操作Service实现
* @createDate 2025-04-16 10:44:26
*/
@Manager
public class SettlementFlowManagerImpl extends ServiceImpl<SettlementFlowMapper, SettlementFlow>
    implements SettlementFlowManager {

}




