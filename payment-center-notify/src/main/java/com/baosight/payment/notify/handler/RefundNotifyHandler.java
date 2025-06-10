package com.baosight.payment.notify.handler;

import cn.hutool.core.net.url.UrlBuilder;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.enums.PayWayCode;
import com.baosight.payment.enums.RefundOrderState;
import com.baosight.payment.notify.pojo.dao.PayOrderNotifyDAO;
import com.baosight.payment.notify.utils.OkHttp;
import com.baosight.payment.order.api.RefundOrderApi;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;
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
@RequiredArgsConstructor
@Component(value = RefundNotifyHandler.MARK)
public class RefundNotifyHandler implements INotifyHandler {
    public static final String MARK = "refundNotifyHandler";

    private final RefundOrderApi refundOrderApi;

    /**
     * 发起通知
     *
     * @param orderId   订单id
     * @param notifyUrl
     */
    @Override
    public String notify(Long orderId, String notifyUrl) {
        log.info("退款订单");
        PayRefundOrderVO payRefundOrder = refundOrderApi.refundOrderInfo(orderId);
        String result;
        try {
            String host = notifyUrl.split("\\?")[0];
            PayOrderNotifyDAO payOrderNotify = new PayOrderNotifyDAO();
            payOrderNotify.setOrderId(payRefundOrder.getRefundNo());
            payOrderNotify.setOriginOrderNo(payRefundOrder.getPayOrderNo());
            payOrderNotify.setCreateTime(String.valueOf(payRefundOrder.getCreateTime().getTime()));
            payOrderNotify.setFinishTime(String.valueOf(payRefundOrder.getSuccessTime().getTime()));
            payOrderNotify.setMchNo(payRefundOrder.getMchNo());
            payOrderNotify.setAppId(payRefundOrder.getAppNo());
            payOrderNotify.setOrderAmount(String.valueOf(payRefundOrder.getRefundAmount()));
            payOrderNotify.setMchOrderNo(payRefundOrder.getMchRefundNo());
            payOrderNotify.setOriginMchOrderNo(payRefundOrder.getOriginMchPayOrderNo());
            RefundOrderState refundOrderState = IBaseEnum.getByCode(RefundOrderState.class, payRefundOrder.getState());
            payOrderNotify.setResultCode(refundOrderState.getState());
            PayWayCode payWayCode = IBaseEnum.getByCode(PayWayCode.class, payRefundOrder.getPayWayCode());
            payOrderNotify.setPayType(payWayCode.getWayCode());
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
     */
    @Override
    public Boolean updateNotifySent(Long orderId, NotifyState notifyState, String notifyUrl) {
        return refundOrderApi.updateNotifySent(orderId, notifyState.getCode());
    }
}
