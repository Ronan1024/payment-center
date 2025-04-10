package com.baosight.payment.manager;

import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.order.api.vo.OrderVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
public interface PayOrderServiceManager {

    /**
     * 根据支付订单id获取支付订单信息
     *
     * @param payOrderId 支付订单Id
     */
    OrderVO payOrderInfo(Long payOrderId);

    /**
     * 支付订单支付成功进行通知
     *
     * @param notifyUrl  通知地址
     * @param notifyType 通知类型
     * @param payOrderId 支付订单id
     * @param mchId      通知商户id
     * @param appId      通知应用id
     */
    Boolean payOrderNotify(String notifyUrl, NotifyType notifyType, Long payOrderId, Long mchId, Long appId);
}
