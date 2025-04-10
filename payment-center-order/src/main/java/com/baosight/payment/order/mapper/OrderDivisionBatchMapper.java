package com.baosight.payment.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.database.page.PageRequest;
import com.baosight.payment.order.pojo.entity.OrderDivisionBatch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.order.pojo.vo.DivisionBatchPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author longjiangran
* @description 针对表【order_division_batch(订单分账批次)】的数据库操作Mapper
* @createDate 2025-04-09 15:35:19
* @Entity com.baosight.payment.order.pojo.entity.OrderDivisionBatch
*/
@Mapper
public interface OrderDivisionBatchMapper extends BaseMapper<OrderDivisionBatch> {

    IPage<DivisionBatchPageVO> page(@Param("page") Page<DivisionBatchPageVO> page, @Param("pageRequest") PageRequest pageRequest);
}




