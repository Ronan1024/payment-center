package com.baosight.payment.controller.refund;

import cn.hutool.core.date.DateUtil;
import com.baosight.common.exception.ServiceException;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.chanel.IRefundService;
import com.baosight.payment.enums.*;
import com.baosight.payment.error.PayOrderError;
import com.baosight.payment.exception.ChannelHandlerException;
import com.baosight.payment.model.refund.RefundOrder;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.RefundOrderApi;
import com.baosight.payment.order.api.dto.CreateRefundOrderDTO;
import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.CreateRefundOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.pojo.vo.ChannelHandlerResult;
import com.baosight.payment.pojo.vo.RefundChannelHandlerResult;
import com.baosight.payment.pojo.vo.RefundOrderResponse;
import com.baosight.payment.utils.StringUtil;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.spring.base.utils.ApplicationContextHolder;
import com.ronan.common.enums.IBaseEnum;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.Assert;
import com.baosight.web.core.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

/**
 * @program: payment-center
 * @description: 商户发起退款申请
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refund")
public class RefundOrderController {
    @Resource
    private OrderApi orderApi;

    @Resource
    private RefundOrderApi refundOrderApi;
    @Resource
    private MchInfoApi mchInfoApi;

    /**
     * 申请退款
     **/
    @PostMapping("/order")
    public RefundOrderResponse refundOrder(@RequestBody RefundOrder request) {
//        RefundOrder refundOrder = null;
        //TODO 获取参数 & 验签
//        RefundOrderRQ rq = getRQByWithMchSign(RefundOrderRQ.class);

        try {
            MchInfoVO mchInfo = mchInfoApi.mchInfoBuMchNO(request.getMchNo());
            Assert.isTrue(!StringUtils.hasText(request.getMchOrderNo()) || !StringUtils.hasText(request.getPayOrderId()), "mchOrderNo 和 payOrderId不能同时为空");

            Assert.isTrue(StringUtils.hasText(request.getNotifyUrl()) && !StringUtil.isAvailableUrl(request.getNotifyUrl()), ApiException.supplier(PayOrderError.NOTIFY_URL_PROTOCOL_ERROR));

            OrderVO order = orderApi.orderInfo(mchInfo.getId(), request.getMchOrderNo(), request.getPayOrderId());

            Assert.isNull(order, "退款订单不存在");
            Assert.isFalse(order.getState().equals(PayOrderState.SUCCESS.code()) || order.getState().equals(PayOrderState.PRE_CONSUMPTION.code()), "订单状态不正确， 无法完成退款");

            Assert.isTrue(order.getRefundState().equals(RefundType.REFUND_TYPE_ALL.code()) || order.getRefundAmount() >= order.getTotalAmount(), "订单已全额退款，本次申请失败");

            long refundAmount = order.getRefundAmount() + Long.parseLong(request.getRefundAmount());
            Assert.isTrue(refundAmount > order.getTotalAmount(), "申请金额超出订单可退款余额，请检查退款金额");

            Long refundOrderCount = refundOrderApi.refundOrderCount(mchInfo.getId(), request.getMchRefundNo(), RefundOrderState.REFUNDING.code());
            Assert.isTrue(refundOrderCount > 0, "支付订单具有在途退款申请，请稍后再试");


            Long sumSuccessRefundAmount = refundOrderApi.sumRefundAmount(order.getId(), RefundOrderState.REFUNDED.code());

            //全部退款金额 （退款订单表）
            Assert.isTrue(sumSuccessRefundAmount >= order.getTotalAmount(), "退款单已完成全部订单退款，本次申请失败");


            // 校验退款单号是否重复
            Long refundOrderByMchOrderNo = refundOrderApi.refundOrderCount(mchInfo.getId(), request.getMchRefundNo(), null);
            Assert.isTrue(refundOrderByMchOrderNo > 0, "商户退款订单号[" + request.getMchRefundNo() + "]已存在");

//
//            //获取支付参数 (缓存数据) 和 商户信息
//            MchAppConfigContext mchAppConfigContext = configContextQueryService.queryMchInfoAndAppInfo(mchNo, appId);
//            if (mchAppConfigContext == null) {
//                throw new BizException("获取商户应用信息失败");
//            }
//
//            MchInfo mchInfo = mchAppConfigContext.getMchInfo();
//            MchApp mchApp = mchAppConfigContext.getMchApp();
//
            //获取退款接口
            PayInterfaceCode payInterfaceCode = IBaseEnum.getByCode(PayInterfaceCode.class, order.getIfCode());
            IRefundService refundService = ApplicationContextHolder.getBean(payInterfaceCode.getCodeName() + "Refund", IRefundService.class);
            Assert.isNull(refundService, "当前通道不支持退款！");

            CreateRefundOrderDTO refundOrder = genRefundOrder(request, order, mchInfo);

            //退款单入库 退款单状态：生成状态  此时没有和任何上游渠道产生交互。
            CreateRefundOrderVO refundOrderResult = refundOrderApi.createRefundOrder(refundOrder);

            // 调起退款接口
            RefundChannelHandlerResult handlerResult = refundService.refund(request, refundOrderResult, order, mchInfo);


            //处理退款单状态
            this.processChannelMsg(handlerResult, refundOrderResult);

            RefundOrderResponse refundOrderResponse = new RefundOrderResponse();
            refundOrderResponse.setMchRefundNo(request.getMchRefundNo());
            refundOrderResponse.setState(String.valueOf(handlerResult.getChannelState()));
            refundOrderResponse.setRefundOrderNo(refundOrderResult.getRefundOrderNo());
            refundOrderResponse.setPayAmount(String.valueOf(order.getTotalAmount()));
            refundOrderResponse.setRefundAmount(String.valueOf(refundOrder.getRefundAmount()));
            return refundOrderResponse;

        } catch (ApiException | ServiceException e) {
            throw e;
        } catch (ChannelHandlerException e) {
            ChannelHandlerResult channelHandlerResult = e.getChannelHandlerResult();
            //处理上游返回数据
            this.processChannelMsg(channelHandlerResult, null);
            if (Objects.equals(e.getChannelHandlerResult().getChannelState(), ChannelState.SYSTEM_ERROR.getCode())) {
                throw new ServiceException(e.getMessage());
            }
            RefundOrderResponse refundOrderResponse = new RefundOrderResponse();
            refundOrderResponse.setRespMsg(e.getMessage());
            return refundOrderResponse;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("系统异常：{}", JsonUtil.toJson(e.getMessage()));
            throw new ApiException(PayOrderError.SYSTEM_ERROR);
        }
    }


    private CreateRefundOrderDTO genRefundOrder(RefundOrder request, OrderVO payOrder, MchInfoVO mchInfo) {
        Date nowTime = new Date();
        CreateRefundOrderDTO refundOrder = new CreateRefundOrderDTO();
        //支付订单号
        refundOrder.setPayOrderId(payOrder.getId());
        //渠道支付单号
        refundOrder.setChannelPayOrderNo(payOrder.getChannelOrderNo());
        //商户号
        refundOrder.setMchNo(mchInfo.getMchNo());
        refundOrder.setMchId(mchInfo.getId());
        //服务商号
        refundOrder.setIsvId(mchInfo.getIsvId());
        //商户应用ID
        // TODO 应用编号待处理
        refundOrder.setAppId(payOrder.getAppId());
        //商户名称
        refundOrder.setMchName(mchInfo.getMchShortName());
        //商户类型
        refundOrder.setMchType(mchInfo.getType());
        //商户退款单号
        refundOrder.setMchRefundNo(request.getMchRefundNo());
        //支付方式代码
        refundOrder.setPayWayCode(payOrder.getWayCode());
        //支付接口代码
        refundOrder.setInterfaceCode(payOrder.getIfCode());
        //支付金额,单位分
        refundOrder.setPayAmount(payOrder.getTotalAmount());
        refundOrder.setPayOrderNo(payOrder.getOrderNo());
        refundOrder.setPayMchOrgOrderNo(payOrder.getMchOrderNo());
        refundOrder.setPayWayId(payOrder.getPayWayId());
        refundOrder.setInterfaceId(payOrder.getInterfaceId());
        //退款金额,单位分
        refundOrder.setRefundAmount(Long.parseLong(request.getRefundAmount()));
        //退款状态:0-订单生成,1-退款中,2-退款成功,3-退款失败
        refundOrder.setState(RefundOrderState.ORDER_GENERATED.code());
        //退款原因
        refundOrder.setRefundReason(request.getRefundReason());
        //渠道订单号
        refundOrder.setChannelOrderNo(null);
        //渠道错误码
        refundOrder.setErrCode(null);
        //渠道错误描述
        refundOrder.setErrMsg(null);
        //特定渠道发起时额外参数
        refundOrder.setChannelExtra(request.getChannelExtra());
        //通知地址
        refundOrder.setNotifyUrl(request.getNotifyUrl());
        //扩展参数
        refundOrder.setExtParam(request.getExtParam());
        //订单超时关闭时间 默认两个小时
        refundOrder.setExpiredTime(DateUtil.offsetHour(nowTime, 2));
        //创建时间
        refundOrder.setCreateTime(nowTime);
        refundOrder.setAppNo(payOrder.getAppNo());
        return refundOrder;
    }


    /**
     * 处理返回的渠道信息，并更新退款单状态
     * payOrder将对部分信息进行 赋值操作。
     **/
    private void processChannelMsg(ChannelHandlerResult handlerResult, CreateRefundOrderVO refundOrder) {

        UpdateRefundOrderState updateRefundOrderState = new UpdateRefundOrderState();
        updateRefundOrderState.setChanelResult(handlerResult.getChannelOriginResponse());
        if (handlerResult instanceof RefundChannelHandlerResult refundChannelHandlerResult) {
            updateRefundOrderState.setChannelOrderNo(refundChannelHandlerResult.getChannelOrderNo());
        }
        updateRefundOrderState.setErrMsg(handlerResult.getChannelErrMsg());
        //对象为空 || 上游返回状态为空， 则无需操作
        if (handlerResult == null || handlerResult.getChannelState() == null) {
            return;
        }

        //明确成功
        if (ChannelState.SUCCESS.getCode().equals(handlerResult.getChannelState())) {
            updateRefundOrderState.setRefundState(RefundOrderState.REFUNDED.code());
            updateRefundOrderState.setRefundId(refundOrder.getRefundOrderId());
            refundOrderApi.updateInitOrderStateThrowException(updateRefundOrderState);
            // TODO 发送回调内容
//            payMchNotifyService.refundOrderNotify(refundOrderService.getById(refundOrder.getRefundOrderId()));
            //明确失败
        } else if (Objects.equals(ChannelState.FAIL.getCode(), handlerResult.getChannelState())) {
            updateRefundOrderState.setRefundState(RefundOrderState.REFUND_FAILED.code());
            updateRefundOrderState.setErrCode(handlerResult.getChannelErrCode());

            refundOrderApi.updateInitOrderStateThrowException(updateRefundOrderState);
            // TODO 发送回调内容
//            payMchNotifyService.refundOrderNotify(refundOrderService.getById(refundOrder.getRefundOrderId()));

            // 上游处理中 || 未知 || 上游接口返回异常  退款单为退款中状态
        } else if (ChannelState.PROCESSING.getCode() == handlerResult.getChannelState() ||
                ChannelState.UNKNOWN.getCode() == handlerResult.getChannelState() ||
                ChannelState.CHANNEL_ERROR.getCode() == handlerResult.getChannelState()) {
            updateRefundOrderState.setRefundState(RefundOrderState.ORDER_GENERATED.code());
            updateRefundOrderState.setRefundId(refundOrder.getRefundOrderId());
            refundOrderApi.updateInitOrderStateThrowException(updateRefundOrderState);

            // 系统异常：  退款单不再处理。  为： 生成状态
        } else if (Objects.equals(ChannelState.SYSTEM_ERROR.getCode(), handlerResult.getChannelState())) {

        } else {
            throw new ServiceException("ChannelState 返回异常！");
        }

    }

}
