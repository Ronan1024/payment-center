package com.baosight.payment.check.service.domain;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.check.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.check.pojo.entity.CheckBatchRecord;
import com.baosight.payment.check.pojo.vo.CheckBatchListVO;

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
