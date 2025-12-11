package com.baosight.payment.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageRequest;
import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.payment.order.mapper.OrderDivisionBatchMapper;
import com.baosight.payment.order.mapper.OrderDivisionRecordMapper;
import com.baosight.payment.order.pojo.entity.OrderDivisionBatch;
import com.baosight.payment.order.pojo.vo.DivisionBatchPageVO;
import com.baosight.payment.order.pojo.vo.DivisionRecordPageVO;
import com.baosight.payment.order.service.OrderDivisionBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author longjiangran
 * @description 针对表【order_division_batch(订单分账批次)】的数据库操作Service实现
 * @createDate 2025-04-09 15:35:19
 */
@Service
@RequiredArgsConstructor
public class OrderDivisionBatchServiceImpl extends ServiceImpl<OrderDivisionBatchMapper, OrderDivisionBatch>
        implements OrderDivisionBatchService {

    private final OrderDivisionBatchMapper orderDivisionBatchMapper;
    private final OrderDivisionRecordMapper orderDivisionRecordMapper;

    @Override
    public PageResponse<DivisionBatchPageVO> divisionBatchPage(PageRequest pageRequest) {
        PageUtil<DivisionBatchPageVO> pageUtil = new PageUtil<>(pageRequest);
        return pageUtil.builder(orderDivisionBatchMapper.page(pageUtil.Page(), pageRequest)).build();
    }

    /**
     * 获取对账记录分页信息
     *
     * @param pageRequest 菜单请求
     * @param batchId     对账批次
     */
    @Override
    public PageResponse<DivisionRecordPageVO> recordPage(PageRequest pageRequest, Long batchId) {
        PageUtil<DivisionRecordPageVO> pageUtil = new PageUtil<>(pageRequest);
        return pageUtil.builder(orderDivisionRecordMapper.recordPage(pageUtil.Page(), batchId)).build();
    }
}




