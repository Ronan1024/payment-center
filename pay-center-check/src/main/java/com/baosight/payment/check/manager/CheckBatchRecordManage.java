package com.baosight.payment.check.manager;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.check.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.check.pojo.vo.CheckBatchListVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
public interface CheckBatchRecordManage {
    /**
     * 获取对账批次列表
     *
     * @param checkBatchPage 对账批次请求体
     */
    PageResponse<CheckBatchListVO> checkBachPage(CheckBatchPageDTO checkBatchPage);

}
