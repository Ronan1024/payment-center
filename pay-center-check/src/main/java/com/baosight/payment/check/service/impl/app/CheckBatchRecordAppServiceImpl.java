package com.baosight.payment.check.service.impl.app;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.check.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.check.pojo.vo.CheckBatchListVO;
import com.baosight.payment.check.service.app.CheckBatchRecordAppService;
import com.baosight.payment.check.service.domain.CheckBatchRecordDomainService;
import com.baosight.payment.annotation.ApplicationService;
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
