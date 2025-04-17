package com.baosight.payment.settlement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.settlement.pojo.entity.AccountFlow;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【account_flow(账单流水)】的数据库操作Mapper
* @createDate 2025-04-16 10:44:26
* @Entity com.baosight.payment.settlement.pojo.entity.AccountFlow
*/
@Mapper
public interface AccountFlowMapper extends BaseMapper<AccountFlow> {

}




