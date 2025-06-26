package com.baosight.payment.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.pojo.dto.CallbackHandlerLogDTO;
import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.pojo.vo.CallbackHandlerLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author longjiangran
 * @description 针对表【callback_handler_log(回调处理记录表)】的数据库操作Mapper
 * @createDate 2025-03-19 14:36:31
 * @Entity com.baosight.payment.pojo.entity.CallbackHandlerLog
 */
@Mapper
public interface CallbackHandlerLogMapper extends BaseMapper<CallbackHandlerLog> {

    /**
     * 分页查询回调处理记录
     *
     * @param page    分页对象
     * @param pageDTO 分页查询参数
     */
    IPage<CallbackHandlerLogVO> page(@Param("page") Page<CallbackHandlerLogVO> page, @Param("pageDTO") CallbackHandlerLogDTO pageDTO);
}




