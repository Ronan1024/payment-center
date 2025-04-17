package com.baosight.payment.check.service.impl.domain;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.payment.check.manager.CheckBatchRecordManage;
import com.baosight.payment.check.mapper.CheckBatchRecordMapper;
import com.baosight.payment.check.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.check.pojo.entity.CheckBatchRecord;
import com.baosight.payment.check.pojo.vo.CheckBatchListVO;
import com.baosight.payment.check.service.domain.CheckBatchRecordDomainService;
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




