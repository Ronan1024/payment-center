package com.baosight.payment.settlement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.settlement.pojo.entity.SettlementRequest;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【settlement_request(结算受理单)】的数据库操作Mapper
* @createDate 2025-04-16 10:44:26
* @Entity com.baosight.payment.settlement.pojo.entity.SettlementRequest
*/
@Mapper
public interface SettlementRequestMapper extends BaseMapper<SettlementRequest> {

}




