package com.baosight.payment.channel.handler.notify;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.channel.enums.ChannelEventType;
import com.baosight.payment.channel.error.ChannelError;
import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.enums.RefundOrderState;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.RefundOrderApi;
import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 退款结果回调处理。
 *
 * @author L.J.Ran
 * @date 2026/04/21
 */
@Component
@RequiredArgsConstructor
public class RefundNotifyOption implements IChannelNotifyOption {

    private final RefundOrderApi refundOrderApi;

    private final NotifyApi notifyApi;

    /**
     * 获取支持的回调事件类型。
     *
     * @return 退款事件类型
     */
    @Override
    public ChannelEventType eventType() {
        return ChannelEventType.REFUND;
    }

    /**
     * 处理退款结果回调。
     *
     * @param dto 统一渠道回调结果
     * @return true 表示处理成功
     */
    @Override
    public Boolean process(UnifiedPayNotifyDTO dto) {
        if (dto.processing()) {
            return true;
        }
        Long refundOrderId = resolveBizId(dto);
        PayRefundOrderVO refundOrder = refundOrderApi.refundOrderInfo(refundOrderId);
        if (refundOrder == null) {
            throw new ServiceException(ChannelError.CALLBACK_REFUND_ORDER_NOT_FOUND.getMsg());
        }
        if (RefundOrderState.REFUNDED.code().equals(refundOrder.getState())) {
            return true;
        }
        if (RefundOrderState.REFUND_FAILED.code().equals(refundOrder.getState()) && dto.fail()) {
            return true;
        }

        UpdateRefundOrderState updateRefundOrderState = new UpdateRefundOrderState();
        updateRefundOrderState.setRefundId(refundOrderId);
        updateRefundOrderState.setRefundState(dto.success() ? RefundOrderState.REFUNDED.code() : RefundOrderState.REFUND_FAILED.code());
        updateRefundOrderState.setChannelOrderNo(dto.getChannelOrderNo());
        updateRefundOrderState.setChanelResult(dto.getRawBody());
        updateRefundOrderState.setFinishTime(dto.getFinishTime());
        updateRefundOrderState.setNotifyTime(dto.getFinishTime());
        if (dto.fail()) {
            updateRefundOrderState.setErrCode(dto.getErrCode());
            updateRefundOrderState.setErrMsg(dto.getErrMsg());
        }

        Boolean updated = refundOrderApi.updateInitOrderStateThrowException(updateRefundOrderState);
        if (Boolean.TRUE.equals(updated) && dto.success()) {
            notifyMerchant(refundOrder, dto);
        }
        return updated;
    }

    private Long resolveBizId(UnifiedPayNotifyDTO dto) {
        if (dto.getBizId() != null) {
            return dto.getBizId();
        }
        // TODO 待处理
//        if (StringUtils.hasText(dto.getBizOrderNo()) && dto.getBizOrderNo().chars().allMatch(Character::isDigit)) {
//            return Long.valueOf(dto.getBizOrderNo());
//        }
        throw new ServiceException(ChannelError.CALLBACK_REFUND_ORDER_ID_MISSING.getMsg());
    }

    private void notifyMerchant(PayRefundOrderVO refundOrder, UnifiedPayNotifyDTO dto) {
        if (!StringUtils.hasText(refundOrder.getNotifyUrl())) {
            return;
        }
        PayOrderNotifyDTO notifyDTO = new PayOrderNotifyDTO()
                .setNotifyUrl(refundOrder.getNotifyUrl())
                .setOrderType(NotifyType.REFUND_SUCCESS.code())
                .setOrderId(refundOrder.getId())
                .setMchId(refundOrder.getMchId())
                .setAppId(refundOrder.getAppId())
                .setIsvId(refundOrder.getIsvId())
                .setFinishTime(dto.getFinishTime());
        notifyApi.payOrderNotify(notifyDTO);
    }
}
