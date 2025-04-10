package com.baosight.payment.accounting.service.impl.domain;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.manager.CheckBatchRecordManage;
import com.baosight.payment.accounting.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.accounting.pojo.entity.CheckBatchRecord;
import com.baosight.payment.accounting.pojo.vo.CheckBatchListVO;
import com.baosight.payment.accounting.service.domain.CheckBatchRecordDomainService;
import com.baosight.payment.accounting.mapper.CheckBatchRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author longjiangran
 * @description 针对表【check_batch_record(对账批次记录)】的数据库操作Service实现
 * @createDate 2025-04-04 21:06:48
 */
@Service
@RequiredArgsConstructor
public class CheckBatchRecordDomainServiceImpl extends ServiceImpl<CheckBatchRecordMapper, CheckBatchRecord> implements CheckBatchRecordDomainService {
    private final CheckBatchRecordManage checkBatchRecordManage;

    /**
     * 获取对账批次列表
     *
     * @param checkBatchPage 对账批次列表请求体
     */
    @Override
    public PageResponse<CheckBatchListVO> checkBachPage(CheckBatchPageDTO checkBatchPage) {
        return checkBatchRecordManage.checkBachPage(checkBatchPage);
    }
}




