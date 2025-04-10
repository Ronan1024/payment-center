package com.baosight.payment.accounting.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.accounting.pojo.entity.CheckRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author longjiangran
 * @description 针对表【check_record(对账记录)】的数据库操作Mapper
 * @createDate 2025-04-08 17:39:01
 * @Entity com.baosight.payment.accounting.pojo.entity.CheckRecord
 */
@Mapper
public interface CheckRecordMapper extends BaseMapper<CheckRecord> {

}




