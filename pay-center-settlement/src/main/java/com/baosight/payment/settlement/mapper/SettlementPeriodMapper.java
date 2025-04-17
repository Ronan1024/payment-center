package com.baosight.payment.settlement.mapper;

import com.baosight.payment.settlement.pojo.entity.SettlementPeriod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【settlement_period(结算账期)】的数据库操作Mapper
* @createDate 2025-04-16 16:45:29
* @Entity com.baosight.payment.settlement.pojo.entity.SettlementPeriod
*/
@Mapper
public interface SettlementPeriodMapper extends BaseMapper<SettlementPeriod> {

}




