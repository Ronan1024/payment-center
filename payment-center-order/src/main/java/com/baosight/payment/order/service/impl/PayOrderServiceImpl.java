package com.baosight.payment.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.service.PayOrderService;
import com.baosight.payment.order.mapper.PayOrderMapper;
import org.springframework.stereotype.Service;

/**
* @author longjiangran
* @description 针对表【pay_order(支付订单表)】的数据库操作Service实现
* @createDate 2025-03-19 14:19:14
*/
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder>
    implements PayOrderService{

}




