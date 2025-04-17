package com.baosight.payment.settlement.mapper;

import com.baosight.payment.settlement.pojo.entity.SettlementFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【settlement_flow(结算流水单表)】的数据库操作Mapper
* @createDate 2025-04-16 10:44:26
* @Entity com.baosight.payment.settlement.pojo.entity.SettlementFlow
*/
@Mapper
public interface SettlementFlowMapper extends BaseMapper<SettlementFlow> {

}




