package com.baosight.payment.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.order.pojo.entity.PayRefundOrder;
import com.baosight.payment.order.service.PayRefundOrderService;
import com.baosight.payment.order.mapper.PayRefundOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author longjiangran
* @description 针对表【pay_refund_order(退款订单表)】的数据库操作Service实现
* @createDate 2025-03-29 12:30:40
*/
@Service
public class PayRefundOrderServiceImpl extends ServiceImpl<PayRefundOrderMapper, PayRefundOrder>
    implements PayRefundOrderService{

}




