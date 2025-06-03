package com.baosight.payment.notify.mq.consumer;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.mq.rocket.domain.MessageWrapper;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.notify.constant.OrderNotifyMQConstant;
import com.baosight.payment.notify.handler.INotifyHandler;
import com.baosight.payment.notify.handler.NotifyHandlerType;
import com.baosight.payment.notify.mq.proudct.PayOrderMchNotifyProduce;
import com.baosight.payment.notify.pojo.dao.PayOrderNotifyMsgDAO;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyConfig;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.service.PayMchNotifyConfigService;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.spring.base.utils.ApplicationContextHolder;
import com.baosight.utils.enums.IBaseEnum;
import com.baosight.utils.utils.ObjectUtils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

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
public class PayOrderMchNotifyConsumer implements RocketMQListener<MessageWrapper<PayOrderNotifyMsgDAO>> {

    private final PayMchNotifyRecordService payMchNotifyRecordService;
    private final PayOrderMchNotifyProduce payOrderMchNotifyProduce;
    private final PayMchNotifyConfigService payMchNotifyConfigService;
    @Resource
    private OrderApi orderApi;

    @Override
    @SuppressWarnings("all")
    public void onMessage(MessageWrapper<PayOrderNotifyMsgDAO> messageWrapper) {
        PayOrderNotifyMsgDAO message = messageWrapper.getMessage();
        Long notifyId = message.getNotifyId();
        PayMchNotifyRecord record = payMchNotifyRecordService.infoId(message.getNotifyId());
        try {
            if (record == null || record.getState() != NotifyState.NOTIFIED.getCode()) {
                log.info("查询通知记录不存在或状态不是通知中");
                return;
            }
            if (record.getNotifyCount() >= record.getNotifyCountLimit()) {
                log.info("已达到最大发送次数");
                return;
            }
            //1. (发送结果最多6次)
            Integer currentCount = record.getNotifyCount() + 1;


            String notifyUrl = record.getNotifyUrl();
            if (ObjectUtils.isEmpty(record.getNotifyUrl())) {
                // TODO (L.J.Ran 2025/3/20 - P1 describe: 处理支付回调地址配置问题)
                PayMchNotifyConfig one = payMchNotifyConfigService.getOne(new LambdaQueryWrapper<PayMchNotifyConfig>()
                        .eq(PayMchNotifyConfig::getMchId, record.getMchId()));
                notifyUrl = one.getNotifyUrl();
            }
            NotifyHandlerType notifyHandlerType = IBaseEnum.getByCode(NotifyHandlerType.class, record.getOrderType());
            log.info("通知处理器：{}, 通知类型：{}", notifyHandlerType.getMsg(), record.getOrderType());
            INotifyHandler notifyHandler = ApplicationContextHolder.getBean(notifyHandlerType.getMsg(), INotifyHandler.class);
            log.info("处理器:{}", notifyHandler);
            String res = notifyHandler.notify(record.getOrderId(), notifyUrl);

            // 第一次通知: 更新为已通知
            if (currentCount == 1) {
                notifyHandler.updateNotifySent(record.getOrderId(), NotifyState.NOTIFIED);
            }

            //通知成功
            if ("SUCCESS".equalsIgnoreCase(res)) {
                payMchNotifyRecordService.updateNotifyResult(notifyId, NotifyState.SUCCESS, res);
                notifyHandler.updateNotifySent(record.getOrderId(), NotifyState.SUCCESS);
                return;
            }

            //通知次数 >= 最大通知次数时， 更新响应结果为异常， 不在继续延迟发送消息
            if (currentCount >= record.getNotifyCountLimit()) {
                payMchNotifyRecordService.updateNotifyResult(notifyId, NotifyState.FAIL, res);
                notifyHandler.updateNotifySent(record.getOrderId(), NotifyState.FAIL);
                return;
            }
            // 更新为失败状态，并记录结果
            payMchNotifyRecordService.updateNotifyResult(notifyId, NotifyState.NOTIFIED, res);
            PayOrderNotifyMsgDAO payOrderNotifyMsgDAO = new PayOrderNotifyMsgDAO();
            payOrderNotifyMsgDAO.setNotifyId(notifyId);
            // TODO 处理通知时间轮
            payOrderNotifyMsgDAO.setDelayLevel(currentCount * 3);
            // 继续发送MQ 延迟发送
            payOrderMchNotifyProduce.sendMessage(payOrderNotifyMsgDAO);
            return;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
    }

}
