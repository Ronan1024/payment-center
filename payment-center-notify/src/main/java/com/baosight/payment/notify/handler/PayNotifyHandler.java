package com.baosight.payment.notify.handler;

import cn.hutool.core.net.url.UrlBuilder;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.enums.PayOrderState;
import com.baosight.payment.enums.PayWayCode;
import com.baosight.payment.notify.pojo.dao.PayOrderNotifyDAO;
import com.baosight.payment.notify.utils.OkHttp;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.utils.enums.IBaseEnum;
import com.baosight.utils.json.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/1
 */
@Slf4j
@Component(value = PayNotifyHandler.MARK)
@RequiredArgsConstructor
public class PayNotifyHandler implements INotifyHandler {
    private final OrderApi orderApi;
    public static final String MARK = "payNotifyHandler";

    /**
     * 发起通知
     *
     * @param orderId   订单id
     * @param notifyUrl 通知url
     */
    @Override
    public String notify(Long orderId, String notifyUrl) {
        log.info("支付订单");
        OrderVO order = orderApi.orderInfo(orderId);
        String result;
        try {
            String host = notifyUrl.split("\\?")[0];
            PayOrderNotifyDAO payOrderNotify = new PayOrderNotifyDAO();
            payOrderNotify.setOrderId(order.getOrderNo());
            payOrderNotify.setCreateTime(String.valueOf(order.getCreateTime().getTime()));
            payOrderNotify.setPayTime(String.valueOf(order.getSuccessTime().getTime()));
            payOrderNotify.setMchNo(order.getMchNo());
            payOrderNotify.setAppId(order.getAppNo());
            payOrderNotify.setPayAmount(String.valueOf(order.getTotalAmount()));
            payOrderNotify.setMchOrderNo(order.getMchOrderNo());
            PayOrderState payOrderState = IBaseEnum.getByCode(PayOrderState.class, order.getState());
            payOrderNotify.setResultCode(payOrderState.getStateCode());
            PayWayCode payWayCode = IBaseEnum.getByCode(PayWayCode.class, order.getWayCode());
            payOrderNotify.setPayType(payWayCode.getWayCode());
            payOrderNotify.setChannelOrderNo(order.getChannelOrderNo());
            payOrderNotify.setPayAgencyChannelOrder(order.getPayAgencyChannelOrder());
            result = OkHttp.postJson(host, JsonUtil.toJson(payOrderNotify));
        } catch (Exception e) {
            log.error("http error", e);
            result = "连接[" + UrlBuilder.of(notifyUrl).getHost() + "]异常:【" + e.getMessage() + "】";
        }
        return result;
    }


    /**
     * 更新通知发送状态
     *
     * @param orderId     订单id
     * @param notifyState 通知状态
     * @param notifyUrl   通知地址
     */
    @Override
    public Boolean updateNotifySent(Long orderId, NotifyState notifyState, String notifyUrl) {
        return orderApi.updateNotifySent(orderId, notifyState.getCode(), notifyUrl);
    }
}
