package com.baosight.payment.notify.handler;

import com.baosight.payment.enums.NotifyState;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/1
 */
public interface INotifyHandler {


    /**
     * 发起通知
     *
     * @param orderId   订单id
     * @param notifyUrl 通知url
     */
    String notify(Long orderId, String notifyUrl);


    /**
     * 更新通知发送状态
     *
     * @param orderId     订单id
     * @param notifyState 通知状态
     */
    Boolean updateNotifySent(Long orderId, NotifyState notifyState);
}
