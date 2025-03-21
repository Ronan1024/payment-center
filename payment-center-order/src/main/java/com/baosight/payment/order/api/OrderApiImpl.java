package com.baosight.payment.order.api;

import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.dto.CrCreateOrderDTO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.order.convert.OrderConvert;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.service.PayOrderService;
import com.baosight.payment.utils.IdGenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @program: payment-center
 * @description: 订单接口处理
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Service
@RequiredArgsConstructor
public class OrderApiImpl implements OrderApi {

    private final PayOrderService payOrderService;
    private final NotifyApi notifyApi;

    /**
     * 码牌支付创建订单
     *
     * @param crCreateOrderDTO 码牌创建订单请求体
     */
    @Override
    public Boolean qCrCreateOrder(CrCreateOrderDTO crCreateOrderDTO) {
        // 创建订单
        PayOrder payOrder = new PayOrder();
        String orderNo = IdGenUtil.generateId(crCreateOrderDTO.getMchId());
        payOrder.setOrderNo(orderNo);
        payOrder.setMchNo(crCreateOrderDTO.getMchNo());
        payOrder.setMchId(crCreateOrderDTO.getMchId());
        payOrder.setIsvId(crCreateOrderDTO.getIsvId());
        payOrder.setAppId(crCreateOrderDTO.getAppId());
        payOrder.setMchName(crCreateOrderDTO.getMchName());
        payOrder.setMchType(crCreateOrderDTO.getMchType());
        payOrder.setMchOrderNo(crCreateOrderDTO.getChannelOrderNo());
        payOrder.setIfCode(crCreateOrderDTO.getInterfaceCode());
        payOrder.setWayCode(crCreateOrderDTO.getWayCode());
        payOrder.setAmount(crCreateOrderDTO.getTotalAmount());
        payOrder.setState(crCreateOrderDTO.getState());
        payOrder.setChannelUser(crCreateOrderDTO.getTradeUser());
        payOrder.setChannelOrderNo(crCreateOrderDTO.getChannelOrderNo());
        payOrder.setHasDivision(crCreateOrderDTO.getHasDivision());
        payOrder.setExtParam(crCreateOrderDTO.getExtParam());
        payOrder.setNotifyUrl(crCreateOrderDTO.getNotifyUrl());
        payOrder.setReturnUrl(crCreateOrderDTO.getReturnUrl());
        payOrder.setSuccessTime(crCreateOrderDTO.getFinishTime());
        payOrder.setCreateTime(crCreateOrderDTO.getCreateTime());
        payOrder.setMchFeeRate(crCreateOrderDTO.getMchFeeRate());
        // TODO (L.J.Ran 2025/3/20 - P0 describe: 待补全 应用编号，服务商编号等信息 处理计算手续费等处理)
        boolean save = payOrderService.save(payOrder);
        if (save) {
            // TODO 通知下游
            PayOrderNotifyDTO payOrderNotifyDTO = new PayOrderNotifyDTO();
            payOrderNotifyDTO.setNotifyUrl(null);
            payOrderNotifyDTO.setOrderType(NotifyType.PAY_SUCCESS.getCode());
            payOrderNotifyDTO.setOrderId(payOrder.getId());
            payOrderNotifyDTO.setMchId(payOrder.getMchId());
            payOrderNotifyDTO.setAppId(payOrder.getAppId());
            notifyApi.payOrderNotify(payOrderNotifyDTO);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取订单信息
     *
     * @param orderId 订单id
     */
    @Override
    public OrderVO orderInfo(Long orderId) {
        PayOrder byId = payOrderService.getById(orderId);
        return OrderConvert.INSTANCE.toOrderVO(byId);
    }
}
