package com.baosight.payment.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baosight.common.exception.ServiceException;
import com.baosight.payment.api.AppMchPassageApi;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.chanel.IPaymentService;
import com.baosight.payment.enums.*;
import com.baosight.payment.error.PayMchPassageError;
import com.baosight.payment.error.PayOrderError;
import com.baosight.payment.manager.PayOrderServiceManager;
import com.baosight.payment.model.order.UnifiedOrder;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.order.api.dto.UpdateOrderState;
import com.baosight.payment.order.api.vo.CreateOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import com.baosight.payment.pojo.vo.UnifiedOrderResponse;
import com.baosight.payment.service.domain.DomainPayOrderService;
import com.baosight.payment.utils.StringUtil;
import com.baosight.payment.vo.MchAppInfoVO;
import com.baosight.payment.vo.MchAppPassageVO;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.spring.base.utils.ApplicationContextHolder;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import com.baosight.web.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainPayOrderServiceImpl implements DomainPayOrderService {


    private final PayOrderServiceManager payOrderServiceManager;

    //    private final PayOrderService payOrderService;
    private final MchInfoApi mchInfoApi;
    private final OrderApi orderApi;
    private final MchAppConfigApi mchAppConfigApi;
    private final AppMchPassageApi appMchPassageApi;
//    private final PayMchPassageService payMchPassageService;

    /**
     * 统一下单
     *
     * @param unifiedOrder 下单请求
     * @param payOrder
     * @return
     */
    @Override
    public UnifiedOrderResponse unifiedOrder(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder) {

        //是否新订单模式   一般接口都为新订单模式 如果已存在则直接更新，否则为插入。
//        boolean isNewOrder = payOrder == null;

        try {
//            //当订单存在时，封装公共参数。
//            if (payOrder != null) {
//                Assert.isFalse(payOrder.getState().equals(PayOrderOption.STATE_INIT.getCode()), ApiException.supplier(PayOrderError.ORDER_STATUS_ERROR));
//                // 需要将订单更新 支付方式
//                payOrder.setWayCode(unifiedOrder.getWayCode());
//                unifiedOrder.setMchId(payOrder.getMchNo());
//                unifiedOrder.setAppId(payOrder.getAppNo());
//                unifiedOrder.setPayAmount(payOrder.getPayAmount());
//                unifiedOrder.setTotalAmount(payOrder.getTotalAmount());
//                unifiedOrder.setPromotionAmount(payOrder.getPromotionAmount());
//                unifiedOrder.setCurrency(payOrder.getCurrency());
//                unifiedOrder.setSubject(payOrder.getSubject());
//                unifiedOrder.setNotifyUrl(payOrder.getNotifyUrl());
//                unifiedOrder.setReturnUrl(payOrder.getReturnUrl());
//                unifiedOrder.setChannelExtra(payOrder.getChannelExtra());
//                unifiedOrder.setExtParam(payOrder.getExtParam());
//                unifiedOrder.setDivisionMode(payOrder.getDivisionMode());
//            }
            // 商户id
            // 商户应用信息
            String appNo = unifiedOrder.getAppId();
            MchInfoVO mchInfo = mchInfoApi.mchInfoBuMchNO(unifiedOrder.getMchId());
            Assert.isNull(mchInfo, ApiException.supplier(PayOrderError.MCH_NOT_FOUND));
            Assert.isFalse(mchInfo.getState().equals(State.NORMAL.getCode()), ApiException.supplier(PayOrderError.MCH_STATUS_ERROR, mchInfo.getMchName()));
            Long mchId = mchInfo.getId();
            MchAppInfoVO mchApp = mchAppConfigApi.mchApiInfo(mchInfo.getId(), appNo);
            Assert.isTrue(mchApp == null || !mchApp.getState().equals(State.NORMAL.getCode()), ApiException.supplier(PayOrderError.APP_STATE_ERROR, mchApp.getAppName()));
            // 只有新订单模式，进行校验
//            if (isNewOrder) {
            int orderCount = orderApi.getPayOrderCount(mchId, unifiedOrder.getOutTradeNo());
            Assert.isTrue(orderCount > 0, ApiException.supplier(PayOrderError.ORDER_EXIST_ERROR, unifiedOrder.getOutTradeNo()));
//            }

            Assert.isTrue(StringUtils.hasText(unifiedOrder.getNotifyUrl()) && !StringUtil.isAvailableUrl(unifiedOrder.getNotifyUrl()), ApiException.supplier(PayOrderError.NOTIFY_URL_PROTOCOL_ERROR));
            Assert.isTrue(StringUtils.hasText(unifiedOrder.getReturnUrl()) && !StringUtil.isAvailableUrl(unifiedOrder.getReturnUrl()), ApiException.supplier(PayOrderError.RETURN_URL_PROTOCOL_ERROR));


//            //获取支付参数 (缓存数据) 和 商户信息
//            MchAppConfigContext mchAppConfigContext = configContextQueryService.queryMchInfoAndAppInfo(mchNo, appId);
//            if (mchAppConfigContext == null) {
//                throw new BizException("获取商户应用信息失败");
//            }


            //收银台支付并且只有新订单需要走这里，  收银台二次下单的wayCode应该为实际支付方式。
//            if (isNewOrder && CS.PAY_WAY_CODE.QR_CASHIER.equals(wayCode)) {
            //生成订单
//            payOrder = genPayOrder(unifiedOrder, mchInfo, mchApp, null);
//            //订单入库 订单状态： 生成状态  此时没有和任何上游渠道产生交互。
//            payOrderService.save(payOrder);
//            Long payOrderId = payOrder.getId();
//
//            QrCashierOrderRS qrCashierOrderRS = new QrCashierOrderRS();
//            QrCashierOrderRQ qrCashierOrderRQ = (QrCashierOrderRQ) bizRQ;
//
//            DBApplicationConfig dbApplicationConfig = sysConfigService.getDBApplicationConfig();
//
//            String payUrl = dbApplicationConfig.genUniJsapiPayUrl(QRCodeParams.TYPE_PAY_ORDER, payOrderId);
//            if (CS.PAY_DATA_TYPE.CODE_IMG_URL.equals(qrCashierOrderRQ.getPayDataType())) { //二维码地址
//                qrCashierOrderRS.setCodeImgUrl(dbApplicationConfig.genScanImgUrl(payUrl));
//
//            } else { //默认都为跳转地址方式
//                qrCashierOrderRS.setPayUrl(payUrl);
//            }
//
//            return packageApiResByPayOrder(bizRQ, qrCashierOrderRS, payOrder);
//        }
//
//
//            }

            MchAppPassageVO mchPayPassage = appMchPassageApi.mchAppPassage(mchId, mchApp.getId(), unifiedOrder.getWayCode());
            Assert.isNull(mchPayPassage, ApiException.supplier(PayMchPassageError.MCH_APP_NONSUPPORT_PAY_WAY));

            //获取支付接口
            IPaymentService paymentService = checkMchWayCodeAndGetService(mchInfo, mchPayPassage, unifiedOrder.getWayCode());
//            Object pay = paymentService.pay(unifiedOrder, mchAppConfigInfoDAO);
            String payInterfaceCode = paymentService.getPayInterfaceCode();
            String payWayCode = paymentService.payWayCode();

            //生成订单
//            if (isNewOrder) {
            // TODO 商户信息
            payOrder = genPayOrder(unifiedOrder, mchInfo, mchApp, mchPayPassage, payInterfaceCode, payWayCode);
//            } else {
            // TODO 支付订单接口编号待处理
//                payOrder.setIfCode(payInterfaceCode);
            // 查询支付方式的费率，并 在更新ing时更新费率信息 // TODO 费率待处理
//                payOrder.setMchFeeRate(mchPayPassage.getRate());
            //商户手续费,单位分 // TODO 待处理
//                payOrder.setMchFeeAmount(AmountUtil.calPercentageFee(payOrder.getAmount(), payOrder.getMchFeeRate()));
//            }

            //预先校验
            ServiceException exception = paymentService.preCheck(unifiedOrder, payOrder);
            Assert.notNull(exception, () -> exception);
            // TODO 商户信息
            String newPayOrderId = paymentService.customPayOrderId(unifiedOrder, payOrder, mchInfo);

            CreateOrderVO createOrderResult = null;
//            if (isNewOrder) {
            if (StringUtils.hasText(newPayOrderId)) {
                // 自定义订单号 处理支付订单号
                payOrder.setOrderNo(newPayOrderId);
            }
            //订单入库 订单状态： 生成状态  此时没有和任何上游渠道产生交互。
            createOrderResult = orderApi.createOrder(payOrder);
            payOrder.setOrderId(createOrderResult.getOrderId());
            payOrder.setOrderNo(createOrderResult.getOrderNo());
//            }


            //调起上游支付接口
            OrderChannelHandlerResult result = paymentService.pay(unifiedOrder, mchInfo, payOrder);

            //TODO 处理上游返回数据
            this.processChannelMsg(result, payOrder, createOrderResult.getOrderId());

            return packageApiResByPayOrder(result, createOrderResult.getOrderNo(), unifiedOrder);
//
//        } catch (BizException e) {
//            return ApiRes.customFail(e.getMessage());
//
//        } catch (ChannelException e) {
//            //处理上游返回数据
//            this.processChannelMsg(e.getChannelRetMsg(), payOrder);
//
//            if (e.getChannelRetMsg().getChannelState() == ChannelRetMsg.ChannelState.SYS_ERROR) {
//                return ApiRes.customFail(e.getMessage());
//            }
//
//            return this.packageApiResByPayOrder(bizRQ, bizRS, payOrder);

//            } catch (BizException e) {
//                return ApiRes.customFail(e.getMessage());
//
//            } catch (ChannelException e) {
//
//                //处理上游返回数据
//                this.processChannelMsg(e.getChannelRetMsg(), payOrder);
//
//                if (e.getChannelRetMsg().getChannelState() == ChannelRetMsg.ChannelState.SYS_ERROR) {
//                    return ApiRes.customFail(e.getMessage());
//                }
//
//                return this.packageApiResByPayOrder(bizRQ, bizRS, payOrder);
//
//
//            } catch (Exception e) {
//                log.error("系统异常：{}", e);
//                return ApiRes.customFail("系统异常");
//            }
//
        } catch (Exception e) {
            log.error("系统异常：{}", e);
            throw new ApiException(PayOrderError.SYSTEM_ERROR);
        }

    }

    /**
     * 明确支付订单为成功时的逻辑处理(除更新订单其他业务)
     *
     * @param payOrder 订单信息
     */
    @Override
    public void confirmSuccess(OrderVO payOrder) {
        // 查询查询订单详情
        payOrder = payOrderServiceManager.payOrderInfo(payOrder.getId());
        //设置订单状态
        payOrder.setState(PayOrderState.SUCCESS.getCode());

        //TODO 自动分账 处理逻辑， 不影响主订单任务
//        this.updatePayOrderAutoDivision(payOrder);

        //发送商户通知
        payOrderServiceManager.payOrderNotify(payOrder.getNotifyUrl(), NotifyType.PAY_SUCCESS, payOrder.getId(), payOrder.getMchId(), payOrder.getAppId());

    }


    private CreateOrderDTO genPayOrder(UnifiedOrder unifiedOrder, MchInfoVO mchInfo, MchAppInfoVO mchApp, MchAppPassageVO payMchPassage, String payInterfaceCode, String payWayCode) {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setMchId(mchInfo.getId());
        createOrderDTO.setMchNo(mchInfo.getMchNo());
        createOrderDTO.setIsvId(mchInfo.getIsvId());
        // TODO 服务商编号待设置
        createOrderDTO.setAppId(mchApp.getId());
        createOrderDTO.setAppNo(mchApp.getAppCode());
        createOrderDTO.setMchName(mchInfo.getMchName());
        createOrderDTO.setMchType(mchInfo.getType());
        createOrderDTO.setIfCode(payInterfaceCode);
        createOrderDTO.setPayAmount(unifiedOrder.getPayAmount());
        createOrderDTO.setPromotionAmount(unifiedOrder.getPromotionAmount());
        createOrderDTO.setTotalAmount(unifiedOrder.getTotalAmount());
        createOrderDTO.setMchOrderNo(unifiedOrder.getOutTradeNo());
        createOrderDTO.setWayCode(payWayCode);
        if (!ObjectUtils.isEmpty(payMchPassage)) {
            createOrderDTO.setMchFeeRate(payMchPassage.getRate());
        } else {
            createOrderDTO.setMchFeeRate(0L);
        }
        createOrderDTO.setState(PayOrderState.INIT.getCode());
        createOrderDTO.setSubject(unifiedOrder.getSubject());
        createOrderDTO.setBody(unifiedOrder.getBody());
        // TODO 分账待处理 ： 是否参与分账 ，  订单分账模式， 订单分账状态
        createOrderDTO.setExtParam(unifiedOrder.getExtParam());
        createOrderDTO.setHasDivision(Boolean.TRUE);
        createOrderDTO.setNotifyUrl(unifiedOrder.getNotifyUrl());
        createOrderDTO.setReturnUrl(unifiedOrder.getReturnUrl());
        createOrderDTO.setType(OrderType.CONSUMPTION.getCode());
        createOrderDTO.setProductType(ProductType.ONLINE_PAYMENT.getCode());
        Date nowDate = new Date();
        if (unifiedOrder.getExpiredTime() != null) {
            createOrderDTO.setExpiredTime(DateUtil.offsetSecond(nowDate, unifiedOrder.getExpiredTime()));
        } else {
            //订单过期时间 默认两个小时
            createOrderDTO.setExpiredTime(DateUtil.offsetHour(nowDate, 2));
        }
        createOrderDTO.setCreateTime(nowDate);
        createOrderDTO.setSignUser(unifiedOrder.getSignUser());
        return createOrderDTO;
    }


    /**
     * 校验： 商户的支付方式是否可用
     * 返回： 支付接口
     **/
    private IPaymentService checkMchWayCodeAndGetService(MchInfoVO mchInfoVO, MchAppPassageVO mchPayPassage, String wayCode) {
        // 接口代码
        IPaymentService paymentService = ApplicationContextHolder.getBean(wayCode, IPaymentService.class);
        Assert.isNull(paymentService, ApiException.supplier(PayOrderError.PAYMENT_CHANNEL_UNAVAILABLE));
        // 处理支付方式
//        Assert.isFalse(paymentService.isSupport(wayCode), ApiException.supplier(PayOrderError.PAY_WAY_NOT_SUPPORT));
        // TODO 支付配置待处理
//普通商户
        if (Objects.equals(mchInfoVO.getType(), MchType.MERCHANT.getCode())) {
//            if (configContextQueryService.queryNormalMchParams(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId(), ifCode) == null) {
//                throw new BizException("商户应用参数未配置");
//            }
//        } else if (mchAppConfigContext.getMchType() == MchInfo.TYPE_ISVSUB) { //特约商户
//
//            if (configContextQueryService.queryIsvsubMchParams(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId(), ifCode) == null) {
//                throw new BizException("特约商户参数未配置");
//            }
//
//            if (configContextQueryService.queryIsvParams(mchAppConfigContext.getMchInfo().getIsvNo(), ifCode) == null) {
//                throw new BizException("服务商参数未配置");
//            }
        }

        return paymentService;
    }


    /**
     * TODO 处理返回的渠道信息，并更新订单状态
     * payOrder将对部分信息进行 赋值操作。
     **/
    private void processChannelMsg(OrderChannelHandlerResult channelResult, CreateOrderDTO payOrder, Long orderId) {

        //对象为空 || 上游返回状态为空， 则无需操作
        if (channelResult == null || channelResult.getChannelState() == null) {
            return;
        }
        UpdateOrderState updateOrderState = new UpdateOrderState();
        updateOrderState.setOrderId(orderId);
        updateOrderState.setChannelOrderNo(channelResult.getChannelOrderNo());
        updateOrderState.setErrCode(channelResult.getChannelErrCode());
        updateOrderState.setErrMsg(channelResult.getChannelErrMsg());
        updateOrderState.setChannelMchNo(channelResult.getChannelMchNo());
        updateOrderState.setType(channelResult.getType());
        updateOrderState.setSubType(channelResult.getSubType());
        updateOrderState.setTradeType(channelResult.getTradeType());
        updateOrderState.setTradeMode(channelResult.getTradeModel());
        // TODO 待记录上游返回结果信息
        //明确成功
        if (ChannelState.SUCCESS.getCode().equals(channelResult.getChannelState())) {
            updateOrderState.setOrderState(PayOrderState.SUCCESS.getCode());
            orderApi.updateInitOrderStateThrowException(updateOrderState);

            //TODO 订单支付成功，其他业务逻辑
//            payOrderProcessService.confirmSuccess(payOrder);

            //明确失败
        } else if (ChannelState.FAIL.getCode() == channelResult.getChannelState()) {
            updateOrderState.setOrderState(PayOrderState.FAIL.getCode());

            orderApi.updateInitOrderStateThrowException(updateOrderState);

            // 上游处理中 || 未知 || 上游接口返回异常  订单为支付中状态
        } else if (ChannelState.PROCESSING.getCode() == channelResult.getChannelState()
                || ChannelState.UNKNOWN.getCode() == channelResult.getChannelState()
                || ChannelState.CHANNEL_ERROR.getCode() == channelResult.getChannelState()) {

            updateOrderState.setOrderState(PayOrderState.PAYING.getCode());
            orderApi.updateInitOrderStateThrowException(updateOrderState);
            channelResult.setPayOrderState(PayOrderState.PAYING.getCode());
            // 系统异常：  订单不再处理。  为： 生成状态
        } else if (ChannelState.SYSTEM_ERROR.getCode() == channelResult.getChannelState()) {

        } else {
            throw new ServiceException("ChannelState 返回异常！");
        }

        // TODO 判断是否需要轮询查单
//        if (channelResult.isNeedQuery()) {
//            mqSender.send(PayOrderReissueMQ.build(payOrderId, 1), 5);
//        }

    }

//
//    /**
//     * 更新订单状态 --》 订单生成--》 其他状态  (向外抛出异常)
//     **/
//    private void updateInitOrderStateThrowException(byte orderState, PayOrder payOrder, ChannelRetMsg channelRetMsg) {
//
//        payOrder.setState(orderState);
//        payOrder.setChannelOrderNo(channelRetMsg.getChannelOrderId());
//        payOrder.setErrCode(channelRetMsg.getChannelErrCode());
//        payOrder.setErrMsg(channelRetMsg.getChannelErrMsg());
//
//        // 聚合码场景 订单对象存在会员信息， 不可全部以上游为准。
//        if (StringUtils.isNotEmpty(channelRetMsg.getChannelUserId())) {
//            payOrder.setChannelUser(channelRetMsg.getChannelUserId());
//        }
//
//        payOrderProcessService.updateIngAndSuccessOrFailByCreatebyOrder(payOrder, channelRetMsg);
//
//    }
//

    /**
     * 统一封装订单数据
     **/
    private UnifiedOrderResponse packageApiResByPayOrder(OrderChannelHandlerResult unifiedOrder, String orderNo, UnifiedOrder payOrder) {
        UnifiedOrderResponse<Object> result = new UnifiedOrderResponse<>();
        result.setOrderState(unifiedOrder.getChannelState());
        result.setErrCode(unifiedOrder.getChannelErrCode());
        result.setErrMsg(unifiedOrder.getChannelErrMsg());
        result.setMchOrderNo(payOrder.getOutTradeNo());
        result.setPayOrderId(orderNo);
        result.setChannelFrontParamInfo(unifiedOrder.getResponse());
        return result;
    }


}
