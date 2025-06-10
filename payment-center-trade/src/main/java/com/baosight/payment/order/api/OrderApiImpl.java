package com.baosight.payment.order.api;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.math.Money;
import com.baosight.payment.check.api.CheckTradingFlowApi;
import com.baosight.payment.check.dto.RegisterTradingFlowDTO;
import com.baosight.payment.enums.*;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.dto.CrCreateOrderDTO;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.order.api.dto.UpdateOrderState;
import com.baosight.payment.order.api.vo.CreateOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.order.convert.OrderConvert;
import com.baosight.payment.order.convert.PayOrderConvert;
import com.baosight.payment.order.manager.PayOrderManager;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.service.domain.PayOrderDomainService;
import com.baosight.payment.settlement.api.SettlementRequestApi;
import com.baosight.payment.settlement.dto.CreateSettlementRequestDTO;
import com.baosight.payment.utils.IdGenUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @program: payment-center
 * @description: 订单接口处理
 * @author: L.J.Ran
 * @create: 2025/3/20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderApiImpl implements OrderApi {

    private final PayOrderDomainService payOrderService;
    private final PayOrderManager payOrderManager;
    private final NotifyApi notifyApi;
    @Resource
    private CheckTradingFlowApi checkTradingFlowApi;

    @Resource
    private SettlementRequestApi settlementRequestApi;

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
        payOrder.setType(crCreateOrderDTO.getType());
        payOrder.setSubType(crCreateOrderDTO.getSubType());
        payOrder.setTradeType(crCreateOrderDTO.getTradeType());
        payOrder.setProductType(crCreateOrderDTO.getProductType());
        payOrder.setChannelResult(crCreateOrderDTO.getChannelResult());
        // TODO (L.J.Ran 2025/3/20 - P0 describe: 待补全 应用编号，服务商编号等信息 处理计算手续费等处理)
        boolean save = payOrderService.save(payOrder);
        if (save) {
            // 记录订单支付日志
            PayOrderNotifyDTO payOrderNotifyDTO = new PayOrderNotifyDTO();
            payOrderNotifyDTO.setNotifyUrl(null).setOrderType(NotifyType.PAY_SUCCESS.getCode()).setOrderId(payOrder.getId())
                    .setMchId(payOrder.getMchId()).setIsvId(payOrder.getIsvId()).setAppId(payOrder.getAppId()).setProductType(payOrder.getProductType());
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
     * @param notifyStatus 通知状态
     */
    @Override
    public Boolean updateNotifySent(Long orderId, Integer notifyStatus) {
        return payOrderService.updateNotifySent(orderId, notifyStatus);
    }

    /**
     * 更新订单状态
     *
     * @param orderId      订单id
     * @param notifyStatus 通知状态
     * @param notifyUrl    通知地址
     */
    @Override
    public Boolean updateNotifySent(Long orderId, Integer notifyStatus, String notifyUrl) {
        return payOrderService.updateNotifySent(orderId, notifyStatus, notifyUrl);
    }

    /**
     * 获取商家指定的订单数量
     *
     * @param mchId      商户ID
     * @param outTradeNo 商家订单号
     */
    @Override
    public int getPayOrderCount(Long mchId, String outTradeNo) {
        return payOrderService.getPayOrderCount(mchId, outTradeNo);
    }

    /**
     * 创建订单
     *
     * @param payOrder 支付订单
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
        order.setTradeType(payOrder.getTradeType());
        order.setTradeMode(payOrder.getTradeMode());
        order.setType(payOrder.getType());
        order.setSubType(payOrder.getSubType());
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
     * @param updateOrderState 更新状态信息
     */
    @Override
    public Boolean updateInitOrderStateThrowException(UpdateOrderState updateOrderState) {
        log.info("更新的数据: {}", updateOrderState);
        PayOrder payOrder = payOrderService.getById(updateOrderState.getOrderId());
        if (!ObjectUtils.isEmpty(updateOrderState.getSubType()) && updateOrderState.getSubType().equals(OrderSubType.WECHAT_ORDER_COMPLETED.getCode())) {
            payOrder.setChannelOriginId(payOrder.getChannelOrderNo());
        }
        payOrder.setState(updateOrderState.getOrderState());
        payOrder.setChannelOrderNo(updateOrderState.getChannelOrderNo());
        payOrder.setErrCode(updateOrderState.getErrCode());
        payOrder.setErrMsg(updateOrderState.getErrMsg());
        payOrder.setSuccessTime(updateOrderState.getFinishTime());
        payOrder.setUpdateTime(new Date());
        payOrder.setChannelResult(updateOrderState.getChannelResult());
        payOrder.setChannelUser(updateOrderState.getChannelUser());
        payOrder.setPayAgencyChannelOrder(updateOrderState.getPayAgencyChannelOrder());
        payOrder.setChannelMchNo(updateOrderState.getChannelMchNo());
        payOrder.setSubType(updateOrderState.getSubType());
        if (updateOrderState.getOrderState().equals(PayOrderState.SUCCESS.getCode()) && payOrder.getSubType().equals(OrderSubType.WECHAT_ORDER_COMPLETED.getCode())) {
            payOrder.setDivisionState(DivisionState.WAITING.getCode());
            DateTime dateTime = DateUtil.offsetDay(new Date(), 1);
            payOrder.setDivisionValidTime(dateTime);
        }


        boolean result = payOrderService.updateById(payOrder);
        if (result && (updateOrderState.getOrderState().equals(PayOrderState.SUCCESS.getCode()) || updateOrderState.getOrderState().equals(PayOrderState.PRE_CONSUMPTION.getCode()))) {
            // 记录对账流水
            RegisterTradingFlowDTO registerTradingFlow = PayOrderConvert.INSTANCE.toRegisterTradingFlowDTO(payOrder);
            checkTradingFlowApi.registerTradingFlow(registerTradingFlow);
            // 创建结算受理单, 注册账期
            CreateSettlementRequestDTO createSettlementRequest = new CreateSettlementRequestDTO();
            //TODO 结算类型待处理
            createSettlementRequest.setType(OrderType.CONSUMPTION.getCode());
            createSettlementRequest.setFirmTime(updateOrderState.getFinishTime());
            BigDecimal divide = new BigDecimal(payOrder.getPayAmount()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            createSettlementRequest.setAmount(new Money(divide));
            // 创建结算金额
            createSettlementRequest.setOrderId(payOrder.getId());
            settlementRequestApi.createSettlementRequest(createSettlementRequest);
        }
        return result;
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
     *
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
