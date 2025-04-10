package com.baosight.payment.order.api;

import com.baosight.payment.enums.NotifyType;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.dto.CrCreateOrderDTO;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.order.api.dto.UpdateOrderState;
import com.baosight.payment.order.api.vo.CreateOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.order.convert.OrderConvert;
import com.baosight.payment.order.manager.PayOrderManager;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.service.domain.PayOrderDomainService;
import com.baosight.payment.utils.IdGenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @program: payment-center
 * @description: 订单接口处理
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Service
@RequiredArgsConstructor
public class OrderApiImpl implements OrderApi {

    private final PayOrderDomainService payOrderService;
    private final PayOrderManager payOrderManager;
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
        payOrder.setPayAmount(crCreateOrderDTO.getPayAmount());
        payOrder.setTotalAmount(crCreateOrderDTO.getTotalAmount());
        payOrder.setPromotionAmount(crCreateOrderDTO.getPromotionAmount());
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
        payOrder.setChannelResult(crCreateOrderDTO.getChannelResult());
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

    /**
     * 更新订单异步通知状态
     *
     * @param orderId      订单id
     * @param notifyStatus
     */
    @Override
    public Boolean updateNotifySent(Long orderId, Integer notifyStatus) {
        return payOrderService.updateNotifySent(orderId, notifyStatus);
    }

    /**
     * 获取商家指定的订单数量
     *
     * @param mchId      商户ID
     * @param outTradeNo 商家订单号
     * @return
     */
    @Override
    public int getPayOrderCount(Long mchId, String outTradeNo) {
        return payOrderService.getPayOrderCount(mchId, outTradeNo);
    }

    /**
     * 创建订单
     *
     * @param payOrder 支付订单
     * @return
     */
    @Override
    public CreateOrderVO createOrder(CreateOrderDTO payOrder) {
        // 创建订单
        PayOrder order = new PayOrder();
        String orderNo;
        if (!StringUtils.hasText(payOrder.getOrderNo())) {
            orderNo = IdGenUtil.generateId(payOrder.getMchId());
        } else {
            orderNo = payOrder.getOrderNo();
        }
        order.setOrderNo(orderNo);
        order.setMchNo(payOrder.getMchNo());
        order.setMchId(payOrder.getMchId());
        order.setIsvId(payOrder.getIsvId());
        order.setAppId(payOrder.getAppId());
        order.setAppNo(payOrder.getAppNo());
        // TODO 服务商编号待处理
        order.setMchName(payOrder.getMchName());
        order.setMchType(payOrder.getMchType());
        order.setMchOrderNo(payOrder.getMchOrderNo());
        order.setIfCode(payOrder.getIfCode());
        order.setWayCode(payOrder.getWayCode());
        order.setPayAmount(payOrder.getPayAmount());
        order.setTotalAmount(payOrder.getTotalAmount());
        order.setPromotionAmount(payOrder.getPromotionAmount());
        order.setState(payOrder.getState());
        order.setChannelUser(payOrder.getChannelUser());
        order.setMchFeeAmount(payOrder.getMchFeeAmount());
        order.setHasDivision(payOrder.getHasDivision());
        order.setExtParam(payOrder.getExtParam());
        order.setNotifyUrl(payOrder.getNotifyUrl());
        order.setReturnUrl(payOrder.getReturnUrl());
        order.setCreateTime(payOrder.getCreateTime());
        order.setMchFeeRate(payOrder.getMchFeeRate());
        order.setSubject(payOrder.getSubject());
        order.setBody(payOrder.getBody());
        order.setChannelExtra(payOrder.getChannelExtra());
        order.setDivisionMode(payOrder.getDivisionMode());
        order.setSignUser(payOrder.getSignUser());
        order.setExpiredTime(payOrder.getExpiredTime());
        // TODO 待优化
        payOrderService.save(order);

        CreateOrderVO result = new CreateOrderVO();
        result.setOrderId(order.getId());
        result.setOrderNo(orderNo);
        return result;
    }

    /**
     * 更新订单状态
     *
     * @param state            订单状态
     * @param updateOrderState 更新状态信息
     */
    @Override
    public Boolean updateInitOrderStateThrowException(Integer state, UpdateOrderState updateOrderState) {
        PayOrder payOrder = payOrderService.getById(updateOrderState.getOrderId());
        payOrder.setState(updateOrderState.getOrderState());
        payOrder.setChannelOrderNo(updateOrderState.getChannelOrderNo());
        payOrder.setErrCode(updateOrderState.getErrCode());
        payOrder.setErrMsg(updateOrderState.getErrMsg());
        payOrder.setSuccessTime(updateOrderState.getFinishTime());
        payOrder.setUpdateTime(new Date());
        payOrder.setChannelResult(updateOrderState.getChannelResult());
        payOrder.setChannelUser(updateOrderState.getChannelUser());
        payOrder.setPayAgencyChannelOrder(updateOrderState.getPayAgencyChannelOrder());
        return payOrderService.updateById(payOrder);
    }

    /**
     * 获取订单信息
     *
     * @param mchId      系统商户id
     * @param mchOrderNo 商户订单号
     * @param payOrderNo 系统订单号
     */
    @Override
    public OrderVO orderInfo(Long mchId, String mchOrderNo, String payOrderNo) {
        return payOrderManager.orderInfo(mchId, mchOrderNo, payOrderNo);
    }

    /**
     * 获取订单列表
     * @param orderId 订单id
     */
    @Override
    public List<OrderVO> orderList(List<Long> orderId) {
        return payOrderManager.orderList(orderId);
    }

    /**
     * 更新订单对账状态
     *
     * @param orderIdList 待更新的订单列表
     */
    @Override
    public Boolean updateOrderCheckState(List<Long> orderIdList) {
        return payOrderManager.updateOrderCheckState(orderIdList);
    }


}
