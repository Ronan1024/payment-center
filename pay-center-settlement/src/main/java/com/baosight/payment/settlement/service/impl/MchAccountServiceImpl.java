package com.baosight.payment.settlement.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.settlement.pojo.entity.MchAccount;
import com.baosight.payment.settlement.service.MchAccountService;
import com.baosight.payment.settlement.mapper.MchAccountMapper;
import org.springframework.stereotype.Service;

/**
* @author longjiangran
* @description 针对表【mch_account(商户账户)】的数据库操作Service实现
* @createDate 2025-04-21 15:28:55
*/
@Service
public class MchAccountServiceImpl extends ServiceImpl<MchAccountMapper, MchAccount>
    implements MchAccountService{

}




