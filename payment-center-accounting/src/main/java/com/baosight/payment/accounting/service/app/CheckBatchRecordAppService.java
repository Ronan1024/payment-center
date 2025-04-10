package com.baosight.payment.accounting.service.app;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.accounting.pojo.vo.CheckBatchListVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
public interface CheckBatchRecordAppService {
    /**
     * 获取对账批次列表
     *
     * @param checkBatchPage 对账批次列表请求参数
     */
    PageResponse<CheckBatchListVO> page(CheckBatchPageDTO checkBatchPage);
}
