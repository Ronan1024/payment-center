package com.baosight.payment.notify.mq.consumer;

import cn.hutool.core.net.url.UrlBuilder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.mq.rocket.domain.MessageWrapper;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.enums.PayWayCode;
import com.baosight.payment.notify.constant.OrderNotifyMQConstant;
import com.baosight.payment.notify.mq.proudct.PayOrderMchNotifyProduce;
import com.baosight.payment.notify.pojo.dao.PayOrderNotifyDAO;
import com.baosight.payment.notify.pojo.dao.PayOrderNotifyMsgDAO;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyConfig;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.service.PayMchNotifyConfigService;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import com.baosight.payment.notify.utils.OkHttp;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.utils.enums.IBaseEnum;
import com.baosight.utils.json.JsonUtil;
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
//    private final SpuService spuService;

    @Override
    @SuppressWarnings("all")
    public void onMessage(MessageWrapper<PayOrderNotifyMsgDAO> messageWrapper) {

        PayOrderNotifyMsgDAO message = messageWrapper.getMessage();
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
            OrderVO order = orderApi.orderInfo(record.getOrderId());
            String notifyUrl = record.getNotifyUrl();
            if (ObjectUtils.isEmpty(record.getNotifyUrl())) {
                // TODO (L.J.Ran 2025/3/20 - P1 describe: 处理支付回调地址配置问题)
                PayMchNotifyConfig one = payMchNotifyConfigService.getOne(new LambdaQueryWrapper<PayMchNotifyConfig>()
                        .eq(PayMchNotifyConfig::getMchId, order.getMchId()));
                notifyUrl = one.getNotifyUrl();
            }
            String res = "";
            try {

                String host = notifyUrl.split("\\?")[0];
                PayOrderNotifyDAO payOrderNotify = new PayOrderNotifyDAO();
                payOrderNotify.setOrderId(order.getOrderNo());
                payOrderNotify.setOrderType(record.getOrderType());
                payOrderNotify.setCreateTime(order.getCreateTime().getTime());
                payOrderNotify.setPayTime(order.getSuccessTime().getTime());
                payOrderNotify.setMchNo(order.getMchNo());
                payOrderNotify.setAppId(order.getAppNo());
                payOrderNotify.setPayAmount(order.getAmount());
                PayWayCode payWayCode = IBaseEnum.getByCode(PayWayCode.class, order.getWayCode());
                payOrderNotify.setPayType(payWayCode.getWayCode());
                res = OkHttp.postJson(host, JsonUtil.toJson(payOrderNotify));
            } catch (Exception e) {
                log.error("http error", e);
                res = "连接[" + UrlBuilder.of(notifyUrl).getHost() + "]异常:【" + e.getMessage() + "】";
            }

            //支付订单 & 第一次通知: 更新为已通知
            if (currentCount == 1 && NotifyType.PAY_SUCCESS.getCode() == record.getOrderType()) {
                // TODO 更新通知
//            payOrderService.updateNotifySent(record.getOrderId());
            }

            //通知成功
            if ("SUCCESS".equalsIgnoreCase(res)) {
                // TODO 处理通知成功
//            mchNotifyRecordService.updateNotifyResult(notifyId, MchNotifyRecord.STATE_SUCCESS, res);
                return;
            }

            //通知次数 >= 最大通知次数时， 更新响应结果为异常， 不在继续延迟发送消息
            if (currentCount >= record.getNotifyCountLimit()) {
                // TODO 处理通知次数
//            mchNotifyRecordService.updateNotifyResult(notifyId, MchNotifyRecord.STATE_FAIL, res);
                return;
            }

            PayOrderNotifyMsgDAO payOrderNotifyMsgDAO = new PayOrderNotifyMsgDAO();
            payOrderNotifyMsgDAO.setNotifyId(payOrderNotifyMsgDAO.getNotifyId());
            payOrderNotifyMsgDAO.setDelayLevel(currentCount * 3);
            // 继续发送MQ 延迟发送
            // TODO 更新发送失败
//            mchNotifyRecordService.updateNotifyResult(notifyId, MchNotifyRecord.STATE_ING, res);
            payOrderMchNotifyProduce.sendMessage(payOrderNotifyMsgDAO);

            return;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }

//        Date date = new Date();
//        long logId = date.getTime();
//        log.info("=========[start]收到活动信息变更消息,开始处理商品信息 id:[{}]=========", logId);
//        log.info("id:[{}]收到消息: {}", logId, message);
//        ThreadLocalUtil.write(AuthConstant.APP_ID, message.getAppId());
//        ThreadLocalUtil.write(AuthConstant.TENANT_ID, message.getTenantId());
//        try {
//            Boolean seckillInfo = spuService.changeSeckillInfo(message.getChangeProductActiveInfoList());
//            Assert.isFalse(seckillInfo, "修改秒杀信息失败");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("=========[Exception]收到活动信息变更消息,商品信息修改失败id:[{}]=========", logId, e);
//            throw new RuntimeException(e);
//        } finally {
//            ThreadLocalUtil.clear();
//        }
//        log.info("=========[start]收到活动信息变更消息,商品信息修改完成id:[{}]=========", logId);

    }
}
