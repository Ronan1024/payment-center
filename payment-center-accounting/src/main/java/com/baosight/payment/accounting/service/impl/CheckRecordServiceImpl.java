package com.baosight.payment.accounting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.accounting.pojo.entity.CheckRecord;
import com.baosight.payment.accounting.service.CheckRecordService;
import com.baosight.payment.accounting.mapper.CheckRecordMapper;
import org.springframework.stereotype.Service;

/**
* @author longjiangran
* @description 针对表【check_record(对账记录)】的数据库操作Service实现
* @createDate 2025-04-08 17:39:01
*/
@Service
public class CheckRecordServiceImpl extends ServiceImpl<CheckRecordMapper, CheckRecord>
    implements CheckRecordService{

}




