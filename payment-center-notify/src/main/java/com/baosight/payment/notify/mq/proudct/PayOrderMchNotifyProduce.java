package com.baosight.payment.notify.mq.proudct;

import cn.hutool.core.util.IdUtil;
import com.baosight.mq.rocket.domain.MessageWrapper;
import com.baosight.mq.rocket.product.AbstractCommonSendProduceTemplate;
import com.baosight.mq.rocket.product.BaseSendExtendDTO;
import com.baosight.payment.notify.constant.OrderNotifyMQConstant;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author L.J.Ran
 */
@Component
public class PayOrderMchNotifyProduce extends AbstractCommonSendProduceTemplate<Long> {

    private final ConfigurableEnvironment environment;

    public PayOrderMchNotifyProduce(@Autowired RocketMQTemplate rocketMQTemplate, @Autowired ConfigurableEnvironment environment) {
        super(rocketMQTemplate);
        this.environment = environment;
    }

    @Override
    protected BaseSendExtendDTO buildBaseSendExtendParam(Long notify) {
        return BaseSendExtendDTO.builder()
                .eventName("商家支付成功回调")
                .keys(String.valueOf(notify))
                .topic(environment.resolvePlaceholders(OrderNotifyMQConstant.PAY_ORDER_NOTIFY_TOPIC_KEY))
                .tag(environment.resolvePlaceholders(OrderNotifyMQConstant.PAY_ORDER_NOTIFY_TOPIC_KEY_TAG))
                .sentTimeout(2000L)
                .build();
    }

    @Override
    protected Message<?> buildMessage(Long sendMessage, BaseSendExtendDTO requestParam) {
        String keys = !StringUtils.hasText(requestParam.getKeys()) ? IdUtil.fastSimpleUUID() : requestParam.getKeys();
        return MessageBuilder
                .withPayload(new MessageWrapper<>(keys, sendMessage))
                .setHeader(MessageConst.PROPERTY_KEYS, keys)
                .setHeader(MessageConst.PROPERTY_TAGS, requestParam.getTag())
                .build()
                ;
    }
}
