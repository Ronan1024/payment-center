package com.baosight.payment.controller.refund;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.chanel.IChannelRefundNoticeService;
import com.baosight.payment.enums.*;
import com.baosight.payment.exception.ChannelHandlerException;
import com.baosight.payment.manager.PayRefundOrderServiceManager;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;
import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baosight.payment.pojo.vo.RefundOrderChannelHandlerResult;
import com.baosight.payment.service.CallbackHandlerLogService;
import com.baosight.spring.base.utils.ApplicationContextHolder;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.enums.IBaseEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/31
 */
@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
public class ChannelRefundNoticeController {

    private final NotifyApi notifyApi;
    private final PayRefundOrderServiceManager payRefundOrderServiceManager;
    private final CallbackHandlerLogService callbackHandlerLogService;

    /**
     * 异步回调入口
     **/
    @RequestMapping(value = {"/api/refund/notify/{interfaceCode}", "/api/refund/notify/{interfaceCode}/{refundOrderId}"})
    public ResponseEntity doNotify(HttpServletRequest request, @PathVariable("interfaceCode") String interfaceCode, @PathVariable(value = "refundOrderId", required = false) Long urlOrderId) {
        Long refundOrderId = null;
        String logPrefix = "进入[" + interfaceCode + "]支付回调：urlOrderId：[" + urlOrderId + "] ";
        log.info("===== {} =====", logPrefix);
        CallbackHandlerLog callbackHandlerLog = new CallbackHandlerLog();
        callbackHandlerLog.setHasHandler(Boolean.FALSE);
        try {
            // 参数有误
            if (!StringUtils.hasText(interfaceCode)) {
                return ResponseEntity.badRequest().body("interfaceCode is empty");
            }

            PayInterfaceCode payInterfaceCode = IBaseEnum.getByCode(PayInterfaceCode.class, interfaceCode);
            //查询退款接口是否存在
            IChannelRefundNoticeService refundNotifyService = ApplicationContextHolder.getBean(payInterfaceCode.getCodeName() + "RefundNotice", IChannelRefundNoticeService.class);
            callbackHandlerLog.setInterfaceCode(interfaceCode);
            // 支付通道接口实现不存在
            if (refundNotifyService == null) {
                log.error("{}, interface not exists ", logPrefix);
                return ResponseEntity.badRequest().body("[" + interfaceCode + "] interface not exists");
            }

            String notifyParam = refundNotifyService.getNotifyParam(request);
            callbackHandlerLog.setCallbackContext(notifyParam);

            // 解析订单号 和 请求参数
            ParseChannelParamDAO parseParams = refundNotifyService.parseParams(notifyParam, refundOrderId);
            // 解析数据失败， 响应已处理
            if (parseParams == null) {
                log.error("{}, parse params is null ", logPrefix);
                throw new ServiceException("解析数据异常！");
            }
            //解析到订单号
            refundOrderId = parseParams.getOrderId();
            log.info("{}, 解析数据为：payOrderId:{}, params:{}", logPrefix, refundOrderId, parseParams);

            if (!ObjectUtils.isEmpty(urlOrderId) && !urlOrderId.equals(refundOrderId)) {
                log.error("{}, 订单号不匹配. urlOrderId={}, refundOrderId={} ", logPrefix, urlOrderId, refundOrderId);
                throw new ServiceException("退款单号不匹配！");
            }

            callbackHandlerLog.setTrxId(parseParams.getChannelOrderId());
            //获取订单号 和 订单数据
            PayRefundOrderVO refundOrder = payRefundOrderServiceManager.payRefundOrderInfo(refundOrderId);
            // 订单不存在
            if (refundOrder == null) {
                log.error("{}, 退款订单不存在 ", logPrefix);
                return refundNotifyService.doNotifyOrderNotExists(request);
            }
            PayWayCode payWayCode = IBaseEnum.getByCode(PayWayCode.class, refundOrder.getPayWayCode());
            callbackHandlerLog.setPayType(payWayCode.getWayCode());
            callbackHandlerLog.setPayingAgency(parseParams.getPayingAgency());


            //查询出商户应用的配置信息
//            MchAppConfigContext mchAppConfigContext = configContextQueryService.queryMchInfoAndAppInfo(refundOrder.getMchNo(), refundOrder.getAppId());

            //调起接口的回调判断
            RefundOrderChannelHandlerResult notifyResult = refundNotifyService.doNotice(request, parseParams);

            // 返回null 表明出现异常， 无需处理通知下游等操作。
            if (notifyResult == null || notifyResult.getChannelState() == null || notifyResult.getResponseEntity() == null) {
                log.error("{}, 处理回调事件异常  notifyResult data error, notifyResult ={} ", logPrefix, notifyResult);
                throw new ServiceException("处理回调事件异常！");
            }
            // 处理退款订单
            boolean updateOrderSuccess = handleRefundOrder4Channel(notifyResult, refundOrder, parseParams);

            // 更新退款订单 异常
            if (!updateOrderSuccess) {
                log.error("{} ", logPrefix);
                return refundNotifyService.doNotifyOrderStateUpdateFail(request);
            }
            log.info("===== {}, 订单通知完成。 refundOrderId={}, parseState = {} =====", logPrefix, refundOrderId, notifyResult.getChannelState());
            callbackHandlerLog.setHasHandler(Boolean.TRUE);
            return notifyResult.getResponseEntity();
        } catch (ApiException e) {
            log.error("{}, refundOrderId={}, BizException", logPrefix, refundOrderId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ChannelHandlerException e) {
            log.error("{}, refundOrderId={}, ResponseException", logPrefix, refundOrderId, e);
            return e.getChannelHandlerResult().getResponseEntity();

        } catch (Exception e) {
            log.error("{}, refundOrderId={}, 系统异常", logPrefix, refundOrderId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } finally {
            callbackHandlerLogService.save(callbackHandlerLog);

        }
    }


    /**
     * 根据通道返回的状态，处理退款订单业务
     **/
    private boolean handleRefundOrder4Channel(RefundOrderChannelHandlerResult channelRetMsg, PayRefundOrderVO refundOrder, ParseChannelParamDAO parseParams) {
        Long refundOrderId = refundOrder.getId();
        UpdateRefundOrderState updateRefundOrderState = new UpdateRefundOrderState();
        updateRefundOrderState.setRefundId(refundOrderId);
        updateRefundOrderState.setChannelOrderNo(refundOrder.getChannelOrderNo());
        updateRefundOrderState.setChanelResult(channelRetMsg.getChannelOriginResponse());
        updateRefundOrderState.setFinishTime(parseParams.getFinishTime());
        updateRefundOrderState.setRefundState(channelRetMsg.getPayOrderState());
        //默认更新成功
        boolean updateOrderSuccess = true;
        PayOrderNotifyDTO payOrderNotifyDTO = new PayOrderNotifyDTO();
        payOrderNotifyDTO.setNotifyUrl(refundOrder.getNotifyUrl());
        payOrderNotifyDTO.setOrderId(refundOrder.getId());
        payOrderNotifyDTO.setMchId(refundOrder.getMchId());
        payOrderNotifyDTO.setAppId(refundOrder.getAppId());
        // 明确退款成功
        if (channelRetMsg.getChannelState() == ChannelState.SUCCESS.getCode()) {
            updateOrderSuccess = payRefundOrderServiceManager.updateRefundOrderState(updateRefundOrderState);
            // 通知商户系统
            if (updateOrderSuccess && StringUtils.hasText(refundOrder.getNotifyUrl()) && updateRefundOrderState.getRefundState().equals(RefundOrderState.REFUNDED.code())) {
                //发送商户通知
                payOrderNotifyDTO.setOrderType(NotifyType.REFUND_SUCCESS.code());
                notifyApi.payOrderNotify(payOrderNotifyDTO);
            }

            //确认失败
        } else if (channelRetMsg.getChannelState() == ChannelState.FAIL.getCode()) {
            updateRefundOrderState.setErrCode(channelRetMsg.getChannelErrCode());
            updateRefundOrderState.setErrMsg(channelRetMsg.getChannelErrMsg());
            // 更新为失败状态
            updateOrderSuccess = payRefundOrderServiceManager.updateRefundOrderState(updateRefundOrderState);
            // 通知商户系统
            if (StringUtils.hasText(refundOrder.getNotifyUrl())) {
                //发送商户通知
                payOrderNotifyDTO.setOrderType(NotifyType.REFUND_SUCCESS.code());
                notifyApi.payOrderNotify(payOrderNotifyDTO);
            }
        }
        return updateOrderSuccess;
    }

}
