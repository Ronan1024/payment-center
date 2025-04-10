package com.baosight.payment.accounting.service.impl.app;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.annotation.ApplicationService;
import com.baosight.payment.accounting.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.accounting.pojo.vo.CheckBatchListVO;
import com.baosight.payment.accounting.service.app.CheckBatchRecordAppService;
import com.baosight.payment.accounting.service.domain.CheckBatchRecordDomainService;
import lombok.RequiredArgsConstructor;

/**
 * 对账批次应用Service
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@ApplicationService
@RequiredArgsConstructor
public class CheckBatchRecordAppServiceImpl implements CheckBatchRecordAppService {
    private final CheckBatchRecordDomainService checkBatchRecordDomainService;

    /**
     * 获取对账批次列表
     *
     * @param checkBatchPage 对账批次列表请求参数
     */
    @Override
    public PageResponse<CheckBatchListVO> page(CheckBatchPageDTO checkBatchPage) {
        return checkBatchRecordDomainService.checkBachPage(checkBatchPage);
    }
}
