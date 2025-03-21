package com.baosight.payment.order.mapper;

import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author longjiangran
* @description 针对表【pay_order(支付订单表)】的数据库操作Mapper
* @createDate 2025-03-19 14:19:14
* @Entity com.baosight.payment.order.pojo.entity.PayOrder
*/
@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

}




