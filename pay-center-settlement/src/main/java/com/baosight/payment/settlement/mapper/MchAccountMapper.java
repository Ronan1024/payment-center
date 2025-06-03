package com.baosight.payment.settlement.mapper;

import com.baosight.payment.settlement.pojo.entity.MchAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

/**
* @author longjiangran
* @description 针对表【mch_account(商户账户)】的数据库操作Mapper
* @createDate 2025-04-21 15:28:55
* @Entity com.baosight.payment.settlement.pojo.entity.MchAccount
*/
@Mapper
public interface MchAccountMapper extends BaseMapper<MchAccount> {

    int updateAccountFrozen(Long mchId, BigDecimal amount);
}




