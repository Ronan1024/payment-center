package com.baosight.payment.accounting.service.domain;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.accounting.pojo.entity.CheckBatchRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.accounting.pojo.vo.CheckBatchListVO;

/**
* @author longjiangran
* @description 针对表【check_batch_record(对账批次记录)】的数据库操作Service
* @createDate 2025-04-04 21:06:48
*/
public interface CheckBatchRecordDomainService extends IService<CheckBatchRecord> {

    /**
     * 获取对账批次列表
     * @param checkBatchPage 对账批次列表请求体
     */
    PageResponse<CheckBatchListVO> checkBachPage(CheckBatchPageDTO checkBatchPage);
}
