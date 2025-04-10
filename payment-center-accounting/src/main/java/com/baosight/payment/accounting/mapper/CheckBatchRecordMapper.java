package com.baosight.payment.accounting.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.accounting.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.accounting.pojo.entity.CheckBatchRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.accounting.pojo.vo.CheckBatchListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author longjiangran
* @description 针对表【check_batch_record(对账批次记录)】的数据库操作Mapper
* @createDate 2025-04-04 21:06:48
* @Entity com.baosight.payment.recon.pojo.entity.CheckBatchRecord
*/
@Mapper
public interface CheckBatchRecordMapper extends BaseMapper<CheckBatchRecord> {

    /**
     * 获取对账批次列表
     * @param page page
     * @param checkBatchPage 对账批次查询列表
     */
    IPage<CheckBatchListVO> checkBachPage(@Param("page") Page<CheckBatchListVO> page, @Param("checkBatchPage") CheckBatchPageDTO checkBatchPage);
}




