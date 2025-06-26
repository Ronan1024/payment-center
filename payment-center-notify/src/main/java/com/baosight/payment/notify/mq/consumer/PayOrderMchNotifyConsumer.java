package com.baosight.payment.notify.mq.consumer;

import com.baosight.mq.rocket.domain.MessageWrapper;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.notify.constant.OrderNotifyMQConstant;
import com.baosight.payment.notify.handler.INotifyHandler;
import com.baosight.payment.notify.handler.NotifyHandlerType;
import com.baosight.payment.notify.pojo.dao.PayOrderNotifyMsgDAO;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.service.PayMchNotifyConfigService;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import com.baosight.spring.base.utils.ApplicationContextHolder;
import com.baosight.utils.enums.IBaseEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 订单支付成功回调
 *
 * @author L.J.Ran
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = OrderNotifyMQConstant.PAY_ORDER_NOTIFY_TOPIC_KEY,
        secretKey = OrderNotifyMQConstant.PAY_ORDER_NOTIFY_TOPIC_KEY_TAG,
        consumerGroup = "pay-center-pay-order-notify-topic-consumer-group")
public class PayOrderMchNotifyConsumer implements RocketMQListener<MessageWrapper<Long>> {

    private final PayMchNotifyRecordService payMchNotifyRecordService;
    private final PayMchNotifyConfigService payMchNotifyConfigService;

    @Override
    @SuppressWarnings("all")
    public void onMessage(MessageWrapper<Long> messageWrapper) {
        Long notifyId = messageWrapper.getMessage();
        PayMchNotifyRecord record = payMchNotifyRecordService.infoId(notifyId);
        try {
            if (record == null || record.getState() != NotifyState.NOTIFIED.getCode()) {
                log.error("查询通知记录不存在或状态不是通知中");
                return;
            }
            if (record.getNotifyCount() >= record.getNotifyCountLimit()) {
                log.info("已达到最大发送次数");
                return;
            }
            //1. (发送结果最多7次)
            Integer currentCount = record.getNotifyCount();
            payMchNotifyRecordService.updateNotifyResult(notifyId, NotifyState.PROCESSING, "", "");

            String notifyUrl = record.getNotifyUrl();
            if (!StringUtils.hasText(record.getNotifyUrl())) {
                notifyUrl = payMchNotifyConfigService.getMchNotifyUrl(record.getProductType(), record.getMchId(), record.getIsvId());
            }
            NotifyHandlerType notifyHandlerType = IBaseEnum.getByCode(NotifyHandlerType.class, record.getOrderType());
            log.info("通知处理器：{}, 通知类型：{}", notifyHandlerType.getMsg(), record.getOrderType());
            INotifyHandler notifyHandler = ApplicationContextHolder.getBean(notifyHandlerType.getMsg(), INotifyHandler.class);

            // 通知状态修改为处理中
            if (currentCount == 1) {
                notifyHandler.updateNotifySent(record.getOrderId(), NotifyState.NOTIFIED, notifyUrl);
            }

            log.info("处理器:{}", notifyHandler);
            String res;
            if (!StringUtils.hasText(notifyUrl)) {
                res = "未获取到通知地址信息";
            } else {
                res = notifyHandler.notify(record.getOrderId(), notifyUrl);
            }

            //通知成功
            if ("SUCCESS".equalsIgnoreCase(res)) {
                payMchNotifyRecordService.updateNotifyResult(notifyId, NotifyState.SUCCESS, res, notifyUrl);
                notifyHandler.updateNotifySent(record.getOrderId(), NotifyState.SUCCESS, notifyUrl);
                return;
            }

            //通知次数 >= 最大通知次数时， 更新响应结果为异常， 不在继续通知
            if (currentCount+1 >= record.getNotifyCountLimit()) {
                payMchNotifyRecordService.updateNotifyResult(notifyId, NotifyState.FAIL, res, notifyUrl);
                notifyHandler.updateNotifySent(record.getOrderId(), NotifyState.FAIL, notifyUrl);
                return;
            }
            // 更新状态为通知中， 记录调用失败信息
            payMchNotifyRecordService.updateNotifyResult(notifyId, NotifyState.NOTIFIED, res, notifyUrl);
            PayOrderNotifyMsgDAO payOrderNotifyMsgDAO = new PayOrderNotifyMsgDAO();
            payOrderNotifyMsgDAO.setNotifyId(notifyId);
            return;
        } catch (Exception e) {
            // 如果报错了不进行下一次处理打印出异常原因
            log.error(e.getMessage(), e);
            return;
        }
    }

}
