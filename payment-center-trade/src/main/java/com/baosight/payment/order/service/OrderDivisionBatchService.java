package com.baosight.payment.order.service;

import com.baosight.database.page.PageRequest;
import com.baosight.database.page.PageResponse;
import com.baosight.payment.order.pojo.entity.OrderDivisionBatch;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.order.pojo.vo.DivisionBatchPageVO;
import com.baosight.payment.order.pojo.vo.DivisionRecordPageVO;

/**
* @author longjiangran
* @description 针对表【order_division_batch(订单分账批次)】的数据库操作Service
* @createDate 2025-04-09 15:35:19
*/
public interface OrderDivisionBatchService extends IService<OrderDivisionBatch> {


    PageResponse<DivisionBatchPageVO> divisionBatchPage(PageRequest pageRequest);

    /**
     * 获取对账记录分页信息
     * @param pageRequest 菜单请求
     * @param batchId 对账批次
     */
    PageResponse<DivisionRecordPageVO> recordPage(PageRequest pageRequest, Long batchId);
}
