package com.baosight.payment.order.service.domain;

import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author longjiangran
 * @description 针对表【pay_order(支付订单表)】的数据库操作Service
 * @createDate 2025-03-19 14:19:14
 */
public interface PayOrderDomainService extends IService<PayOrder> {

    /**
     * 修改订单状态为已通知
     *
     * @param orderId      订单id
     * @param notifyStatus 异步通知状态
     */
    Boolean updateNotifySent(Long orderId, Integer notifyStatus);

    /**
     * 获取当前商家指定订单数量
     *
     * @param mchId      商家号
     * @param outTradeNo 商户订单号
     */
    int getPayOrderCount(Long mchId, String outTradeNo);
}
