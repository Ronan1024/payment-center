package com.baosight.payment.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.order.pojo.entity.PayRefundOrder;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【pay_refund_order(退款订单表)】的数据库操作Mapper
* @createDate 2025-03-29 12:30:40
* @Entity com.baosight.payment.order.pojo.entity.PayRefundOrder
*/
@Mapper
public interface PayRefundOrderMapper extends BaseMapper<PayRefundOrder> {


}




