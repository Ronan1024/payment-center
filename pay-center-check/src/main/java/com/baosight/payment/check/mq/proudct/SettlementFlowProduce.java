package com.baosight.payment.check.mq.proudct;

import cn.hutool.core.util.IdUtil;
import com.baosight.mq.rocket.domain.MessageWrapper;
import com.baosight.mq.rocket.product.AbstractCommonSendProduceTemplate;
import com.baosight.mq.rocket.product.BaseSendExtendDTO;
import com.baosight.payment.settlement.constant.SettlementMQConstant;
import com.baosight.payment.settlement.dao.SettlementFlowDAO;
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
public class SettlementFlowProduce extends AbstractCommonSendProduceTemplate<SettlementFlowDAO> {

    private final ConfigurableEnvironment environment;

    public SettlementFlowProduce(@Autowired RocketMQTemplate rocketMQTemplate, @Autowired ConfigurableEnvironment environment) {
        super(rocketMQTemplate);
        this.environment = environment;
    }

    @Override
    protected BaseSendExtendDTO buildBaseSendExtendParam(SettlementFlowDAO settlementFlowDAO) {
        return BaseSendExtendDTO.builder()
                .eventName("通知结算中心开始结算")
                .keys(String.valueOf(settlementFlowDAO.getOrderId()))
                .topic(environment.resolvePlaceholders(SettlementMQConstant.ORDER_SETTLE_TOPIC_KEY))
                .tag(environment.resolvePlaceholders(SettlementMQConstant.ORDER_SETTLE_TOPIC_KEY_TAG))
                .sentTimeout(2000L)
                .build();
    }

    @Override
    protected Message<?> buildMessage(SettlementFlowDAO sendMessage, BaseSendExtendDTO requestParam) {
        String keys = !StringUtils.hasText(requestParam.getKeys()) ? IdUtil.fastSimpleUUID() : requestParam.getKeys();
        return MessageBuilder
                .withPayload(new MessageWrapper<>(keys, sendMessage))
                .setHeader(MessageConst.PROPERTY_KEYS, keys)
                .setHeader(MessageConst.PROPERTY_TAGS, requestParam.getTag())
                .build()
                ;
    }
}
