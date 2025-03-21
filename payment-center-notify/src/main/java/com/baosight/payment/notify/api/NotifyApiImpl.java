package com.baosight.payment.notify.api;

import com.baosight.payment.constant.DelayLevel;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.notify.mq.proudct.PayOrderMchNotifyProduce;
import com.baosight.payment.notify.pojo.dao.PayOrderNotifyMsgDAO;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final PayOrderMchNotifyProduce payOrderMchNotifyProduce;

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
        payMchNotifyRecord.setNotifyUrl(payOrderNotifyDTO.getNotifyUrl());
        payMchNotifyRecord.setNotifyCount(1);
        payMchNotifyRecord.setNotifyCountLimit(6);
        payMchNotifyRecord.setOrderId(payOrderNotifyDTO.getOrderId());
        payMchNotifyRecord.setOrderType(payOrderNotifyDTO.getOrderType());
        payMchNotifyRecord.setState(NotifyState.NOTIFIED.getCode());
        // TODO 处理商户号等问题
        boolean save = payMchNotifyRecordService.save(payMchNotifyRecord);
        if (save) {
            // 发送mq
            PayOrderNotifyMsgDAO payOrderNotifyMsgDAO = new PayOrderNotifyMsgDAO();
            payOrderNotifyMsgDAO.setNotifyId(payMchNotifyRecord.getId());
            payOrderNotifyMsgDAO.setDelayLevel(DelayLevel.ONE_SECOND);
            payOrderMchNotifyProduce.sendMessage(payOrderNotifyMsgDAO);
        }
    }
}
