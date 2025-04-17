package com.baosight.payment.check.mapper;

import com.baosight.payment.check.pojo.entity.TradingFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author longjiangran
 * @description 针对表【trading_flow(交易流水 注册至对账中心)】的数据库操作Mapper
 * @createDate 2025-04-15 22:12:52
 * @Entity com.baosight.payment.check.pojo.entity.TradingFlow
 */
@Mapper
public interface TradingFlowMapper extends BaseMapper<TradingFlow> {

}




