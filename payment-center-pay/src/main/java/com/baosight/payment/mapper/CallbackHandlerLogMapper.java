package com.baosight.payment.mapper;

import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【callback_handler_log(回调处理记录表)】的数据库操作Mapper
* @createDate 2025-03-19 14:36:31
* @Entity com.baosight.payment.pojo.entity.CallbackHandlerLog
*/
@Mapper
public interface CallbackHandlerLogMapper extends BaseMapper<CallbackHandlerLog> {

}




