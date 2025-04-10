package com.baosight.payment.accounting.manager.impl;

import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.payment.accounting.manager.CheckBatchRecordManage;
import com.baosight.payment.accounting.mapper.CheckBatchRecordMapper;
import com.baosight.payment.accounting.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.accounting.pojo.vo.CheckBatchListVO;
import com.baosight.payment.annotation.Manager;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@Manager
@RequiredArgsConstructor
public class CheckBatchRecordManageImpl implements CheckBatchRecordManage {

    private final CheckBatchRecordMapper checkBatchRecordMapper;


    /**
     * 获取对账批次列表
     *
     * @param checkBatchPage 对账批次请求体
     */
    @Override
    public PageResponse<CheckBatchListVO> checkBachPage(CheckBatchPageDTO checkBatchPage) {
        PageUtil<CheckBatchListVO> pageUtil = new PageUtil<>(checkBatchPage);
        return pageUtil.builder(checkBatchRecordMapper.checkBachPage(pageUtil.Page(), checkBatchPage)).build();
    }

}
