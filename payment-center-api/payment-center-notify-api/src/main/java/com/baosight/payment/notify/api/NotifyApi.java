package com.baosight.payment.notify.api;

import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;

/**
 * @program: payment-center
 * @description: 通知处理
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
public interface NotifyApi {


    /**
     * 支付接口通知
     *
     * @param payOrderNotifyDTO 支付接口通知
     */
    void payOrderNotify(PayOrderNotifyDTO payOrderNotifyDTO);

}
