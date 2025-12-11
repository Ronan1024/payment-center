package com.baosight.payment.manager.impl;

import com.baosight.payment.annotation.Manager;
import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.manager.PayOrderServiceManager;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.vo.OrderVO;
import lombok.RequiredArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Manager
@RequiredArgsConstructor
public class PayOrderServiceManagerImpl implements PayOrderServiceManager {
    private final OrderApi orderApi;
    private final NotifyApi notifyApi;

    /**
     * 根据支付订单id获取支付订单信息
     *
     * @param payOrderId 支付订单Id
     */
    @Override
    public OrderVO payOrderInfo(Long payOrderId) {
        return orderApi.orderInfo(payOrderId);
    }


    /**
     * 支付订单支付成功进行通知
     *
     * @param notifyUrl  通知地址
     * @param notifyType 通知类型
     * @param payOrderId 支付订单id
     * @param mchId      通知商户id
     * @param appId      通知应用id
     */
    @Override
    public Boolean payOrderNotify(String notifyUrl, NotifyType notifyType, Long payOrderId, Long mchId, Long appId) {
        //发送商户通知
        PayOrderNotifyDTO payOrderNotifyDTO = new PayOrderNotifyDTO();
        payOrderNotifyDTO.setNotifyUrl(notifyUrl);
        payOrderNotifyDTO.setOrderType(notifyType.code());
        payOrderNotifyDTO.setOrderId(payOrderId);
        payOrderNotifyDTO.setMchId(mchId);
        payOrderNotifyDTO.setAppId(appId);
        notifyApi.payOrderNotify(payOrderNotifyDTO);
        return Boolean.TRUE;
    }
}
