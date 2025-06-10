package com.baosight.payment.notify.api;

import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.notify.constant.NotifyLevelConstant;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: payment-center
 * @description: 异步通知接口处理
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Service
@RequiredArgsConstructor
public class NotifyApiImpl implements NotifyApi {
    private final PayMchNotifyRecordService payMchNotifyRecordService;

    /**
     * 支付接口通知
     *
     * @param payOrderNotifyDTO 支付接口通知
     */
    @Override
    public void payOrderNotify(PayOrderNotifyDTO payOrderNotifyDTO) {
        PayMchNotifyRecord payMchNotifyRecord = new PayMchNotifyRecord();
        payMchNotifyRecord.setMchId(payOrderNotifyDTO.getMchId());
        payMchNotifyRecord.setAppId(payOrderNotifyDTO.getAppId());
        payMchNotifyRecord.setIsvId(payOrderNotifyDTO.getIsvId());
        payMchNotifyRecord.setNotifyUrl(payOrderNotifyDTO.getNotifyUrl());
        payMchNotifyRecord.setNotifyCount(1);
        //TODO 处理通知次数问题
        payMchNotifyRecord.setNotifyCountLimit(7);
        payMchNotifyRecord.setOrderId(payOrderNotifyDTO.getOrderId());
        payMchNotifyRecord.setOrderType(payOrderNotifyDTO.getOrderType());
        payMchNotifyRecord.setState(NotifyState.NOTIFIED.getCode());
        payMchNotifyRecord.setProductType(payOrderNotifyDTO.getProductType());
        payMchNotifyRecord.setNextNotifyTime(NotifyLevelConstant.getNotifyTime(new Date(), payMchNotifyRecord.getNotifyCount()));
        // TODO 处理商户号等问题
        payMchNotifyRecordService.save(payMchNotifyRecord);
    }
}
