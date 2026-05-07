package com.baosight.payment.channel.handler.notify;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.error.ChannelError;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.enums.PayOrderState;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.dto.UpdateOrderState;
import com.baosight.payment.order.api.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 支付下单结果回调处理。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Component
@RequiredArgsConstructor
public class PayOrderNotifyOption implements IChannelNotifyOption {

    private final OrderApi orderApi;

    private final NotifyApi notifyApi;

    /**
     * 获取支持的回调事件类型。
     *
     * @return 支付下单事件类型
     */
    @Override
    public ChannelEventType eventType() {
        return ChannelEventType.PAY_ORDER;
    }

    /**
     * 处理支付下单结果回调。
     *
     * @param dto 统一渠道回调结果
     * @return true 表示处理成功
     */
    @Override
    public Boolean process(UnifiedPayNotifyDTO dto) {
        if (dto.processing()) {
            return true;
        }
        Long orderId = resolveBizId(dto);
        OrderVO payOrder = orderApi.orderInfo(orderId);
        if (payOrder == null) {
            throw new ServiceException(ChannelError.CALLBACK_PAY_ORDER_NOT_FOUND.getMsg());
        }
        if (PayOrderState.SUCCESS.code().equals(payOrder.getState())) {
            return true;
        }
        if (PayOrderState.FAIL.code().equals(payOrder.getState()) && dto.fail()) {
            return true;
        }

        UpdateOrderState updateOrderState = new UpdateOrderState();
        updateOrderState.setOrderId(orderId);
        updateOrderState.setOrderState(dto.success() ? PayOrderState.SUCCESS.code() : PayOrderState.FAIL.code());
        updateOrderState.setChannelMchNo(dto.getChannelMchNo());
        updateOrderState.setChannelUser(dto.getChannelUser());
        updateOrderState.setChannelOrderNo(dto.getChannelOrderNo());
        updateOrderState.setPayAgencyChannelOrder(dto.getChannelOrderNo());
        updateOrderState.setFinishTime(dto.getFinishTime());
        updateOrderState.setChannelResult(dto.getRawBody());
        if (dto.fail()) {
            updateOrderState.setErrCode(dto.getErrCode());
            updateOrderState.setErrMsg(dto.getErrMsg());
        }

        Boolean updated = orderApi.updateInitOrderStateThrowException(updateOrderState);
        if (Boolean.TRUE.equals(updated) && dto.success()) {
            notifyMerchant(payOrder, dto);
        }
        return updated;
    }

    private Long resolveBizId(UnifiedPayNotifyDTO dto) {
        if (dto.getBizId() != null) {
            return dto.getBizId();
        }
//        if (StringUtils.hasText(dto.getBizOrderNo()) && dto.getBizOrderNo().chars().allMatch(Character::isDigit)) {
//            return Long.valueOf(dto.getBizOrderNo());
//        }
        throw new ServiceException(ChannelError.CALLBACK_PAY_ORDER_ID_MISSING.getMsg());
    }

    private void notifyMerchant(OrderVO payOrder, UnifiedPayNotifyDTO dto) {
        if (!StringUtils.hasText(payOrder.getNotifyUrl())) {
            return;
        }
        PayOrderNotifyDTO notifyDTO = new PayOrderNotifyDTO()
                .setNotifyUrl(payOrder.getNotifyUrl())
                .setOrderType(NotifyType.PAY_SUCCESS.code())
                .setOrderId(payOrder.getId())
                .setMchId(payOrder.getMchId())
                .setAppId(payOrder.getAppId())
                .setIsvId(payOrder.getIsvId())
                .setFinishTime(dto.getFinishTime());
        notifyApi.payOrderNotify(notifyDTO);
    }
}
