package com.baosight.payment.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.order.pojo.entity.OrderDivisionRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.order.pojo.vo.DivisionRecordPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author longjiangran
* @description 针对表【order_division_record(订单分账记录)】的数据库操作Mapper
* @createDate 2025-04-09 15:38:59
* @Entity com.baosight.payment.order.pojo.entity.OrderDivisionRecord
*/
@Mapper
public interface OrderDivisionRecordMapper extends BaseMapper<OrderDivisionRecord> {

    IPage<DivisionRecordPageVO> recordPage(@Param("page") Page<DivisionRecordPageVO> page, @Param("batchId") Long batchId);
}




