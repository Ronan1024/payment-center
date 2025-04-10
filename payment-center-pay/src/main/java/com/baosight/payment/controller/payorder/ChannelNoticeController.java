package com.baosight.payment.controller.payorder;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.api.PayInterfaceApi;
import com.baosight.payment.chanel.IChannelNoticeService;
import com.baosight.payment.enums.*;
import com.baosight.payment.error.NoticeError;
import com.baosight.payment.notify.api.NotifyApi;
import com.baosight.payment.notify.api.dto.PayOrderNotifyDTO;
import com.baosight.payment.order.api.OrderApi;
import com.baosight.payment.order.api.dto.CrCreateOrderDTO;
import com.baosight.payment.order.api.dto.UpdateOrderState;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.pojo.dao.ParseChannelParamDAO;
import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import com.baosight.payment.service.CallbackHandlerLogService;
import com.baosight.payment.utils.SybUtil;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.spring.base.utils.ApplicationContextHolder;
import com.baosight.utils.enums.IBaseEnum;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import com.baosight.web.annotation.IgnoreHandlerResponse;
import com.baosight.web.exception.ApiException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
// TODO  关闭订单问题
// TODO 订单状态查询 此处需要进行逻辑处理， 如果支付

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class ChannelNoticeController {

    @Resource
    private CallbackHandlerLogService callbackHandlerLogService;

    @Resource
    private PayInterfaceApi payInterfaceApi;

    @Resource
    private OrderApi orderApi;

    @Resource
    private MchInfoApi mchInfoApi;
    @Resource
    private NotifyApi notifyApi;

    // TODO 根据接口code与收银宝号获取支付配置
    // TODO 根据收银宝号获取商家信息
    // TODO 创建订单 进行数据回调

    /**
     * @return
     */
    @IgnoreHandlerResponse
    @GetMapping("/{payingAgency}/{payType}")
    public String tongLianGet(@PathVariable("payingAgency") Integer payingAgency,
                              @PathVariable("payType") Integer payType,
                              HttpServletRequest request) {
        Map<String, String> params = getParams(request);
        String data = JsonUtil.toJson(params);
        log.info("收到通联回调支付企业: {} 支付类型: {}, 请求数据{}", payingAgency, payType, data);
//        TongL tonglianReturn = new TonglianReturn();
//        tonglianReturn.setResult(data);
//        tonglianReturnService.save(tonglianReturn);
        return "success";
    }

    /**
     * 异步回调入口
     **/
    @IgnoreHandlerResponse
    @RequestMapping(value = {"/api/pay/notify/{interfaceCode}", "/api/pay/notify/{interfaceCode}/{payOrderId}"})
    public ResponseEntity doNotify(HttpServletRequest request, @PathVariable("interfaceCode") String interfaceCode, @PathVariable(value = "payOrderId", required = false) Long urlOrderId) {

        Long payOrderId = null;
        String logPrefix = "进入[" + interfaceCode + "]支付回调：urlOrderId：[" + urlOrderId + "] ";
        log.info("===== {} =====", logPrefix);
        // TODO 回调信息待保存
        CallbackHandlerLog callbackHandlerLog = new CallbackHandlerLog();
        callbackHandlerLog.setHasHandler(Boolean.FALSE);
        try {
            // 参数有误
            if (!StringUtils.hasText(interfaceCode)) {
                return ResponseEntity.badRequest().body("interfaceCode is empty");
            }

            //查询支付接口是否存在
            PayInterfaceCode payInterfaceCode = IBaseEnum.getByCode(PayInterfaceCode.class, interfaceCode);
            IChannelNoticeService payNotifyService = ApplicationContextHolder.getBean(payInterfaceCode.getCodeName() + "Notice", IChannelNoticeService.class);
            String notifyParam = payNotifyService.getBody(request);


            callbackHandlerLog.setCallbackContext(notifyParam);
            callbackHandlerLog.setInterfaceCode(interfaceCode);
//            callbackHandlerLog.setMchNo(mchInterfaceConfig.getMchNo());
            // 预先保存
            // 支付通道接口实现不存在
            if (payNotifyService == null) {
                log.error("{}, interface not exists ", logPrefix);
                return ResponseEntity.badRequest().body("[" + interfaceCode + "] interface not exists");
            }

            // 解析订单号 和 请求参数
            ParseChannelParamDAO parseParams = payNotifyService.parseParams(notifyParam, urlOrderId, IChannelNoticeService.NoticeTypeEnum.DO_NOTIFY);
            // 解析数据失败， 响应已处理
            if (parseParams == null) {
                log.error("{}, parse params is null ", logPrefix);
                throw new ServiceException("解析数据异常！");
            }
            //解析到订单号
            payOrderId = parseParams.getOrderId();
            log.info("{}, 解析数据为：payOrderId:{}, params:{}", logPrefix, payOrderId, parseParams);

            if (!ObjectUtils.isEmpty(urlOrderId) && !urlOrderId.equals(payOrderId)) {
                log.error("{}, 订单号不匹配. urlOrderId={}, payOrderId={} ", logPrefix, urlOrderId, payOrderId);
                throw new ServiceException("订单号不匹配！");
            }
            callbackHandlerLog.setTrxId(parseParams.getChannelOrderId());
            //获取订单号 和 订单数据
            OrderVO payOrder = orderApi.orderInfo(payOrderId);
            // 订单不存在
            if (payOrder == null) {
                log.error("{}, 订单不存在. payOrderId={} ", logPrefix, payOrderId);
                return payNotifyService.doNotifyOrderNotExists(request);
            }

            PayWayCode payWayCode = IBaseEnum.getByCode(PayWayCode.class, payOrder.getWayCode());
            callbackHandlerLog.setPayType(payWayCode.getWayCode());
            callbackHandlerLog.setPayingAgency(parseParams.getPayingAgency());


//            //查询出商户应用的配置信息
//            MchAppConfigContext mchAppConfigContext = configContextQueryService.queryMchInfoAndAppInfo(payOrder.getMchNo(), payOrder.getAppId());


            //调起接口的回调判断
            OrderChannelHandlerResult notifyResult = payNotifyService.doNotice(request, parseParams);

            // 返回null 表明出现异常， 无需处理通知下游等操作。
            if (notifyResult == null || notifyResult.getChannelState() == null || notifyResult.getResponseEntity() == null) {
                log.error("{}, 处理回调事件异常  notifyResult data error, notifyResult ={} ", logPrefix, notifyResult);
                throw new ServiceException("处理回调事件异常！");
            }

            boolean updateOrderSuccess = true; //默认更新成功
            // 订单是 【支付中状态】
            if (Objects.equals(payOrder.getState(), PayOrderState.PAYING.getCode())) {
                //明确成功
                if (ChannelState.SUCCESS.getCode().equals(notifyResult.getChannelState())) {
                    UpdateOrderState updateOrderState = new UpdateOrderState();
                    updateOrderState.setOrderId(payOrderId);
                    updateOrderState.setOrderState(notifyResult.getPayOrderState());
                    updateOrderState.setFinishTime(parseParams.getFinishTime());
                    updateOrderState.setChannelUser(parseParams.getChannelUserId());
                    updateOrderState.setChannelResult(notifyParam);
                    updateOrderState.setPayAgencyChannelOrder(parseParams.getPayAgencyChannelOrder());
                    updateOrderSuccess = orderApi.updateInitOrderStateThrowException(PayOrderState.SUCCESS.getCode(), updateOrderState);
                } else if (ChannelState.FAIL.getCode().equals(notifyResult.getChannelState())) {
                    UpdateOrderState updateOrderState = new UpdateOrderState();
                    updateOrderState.setOrderId(payOrderId);
                    updateOrderState.setOrderState(notifyResult.getPayOrderState());
                    updateOrderState.setFinishTime(parseParams.getFinishTime());
                    updateOrderState.setChannelUser(parseParams.getChannelUserId());
                    updateOrderState.setErrCode(notifyResult.getChannelErrCode());
                    updateOrderState.setErrMsg(notifyResult.getChannelErrMsg());
                    updateOrderState.setChannelResult(notifyParam);
                    updateOrderSuccess = orderApi.updateInitOrderStateThrowException(PayOrderState.FAIL.getCode(), updateOrderState);
                }
            }

            // 更新订单 异常
            if (!updateOrderSuccess) {
                log.error("{}, updateOrderSuccess = {} ", logPrefix, updateOrderSuccess);
                return payNotifyService.doNotifyOrderStateUpdateFail(request);
            }

            //订单支付成功 其他业务逻辑
            if (notifyResult.getChannelState().equals(ChannelState.SUCCESS.getCode())) {
                confirmSuccess(payOrder);
            }

            log.info("===== {}, 订单通知完成。 payOrderId={}, parseState = {} =====", logPrefix, payOrderId, notifyResult.getChannelState());

            return notifyResult.getResponseEntity();

        } catch (SecurityException e) {
            callbackHandlerLog.setHandlerError(JsonUtil.toJson(e));
            log.error("{}, payOrderId={}, BizException", logPrefix, payOrderId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            callbackHandlerLog.setHandlerError(JsonUtil.toJson(e.getCause()));
            log.error("{}, payOrderId={}, 系统异常", logPrefix, payOrderId, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } finally {
            callbackHandlerLogService.save(callbackHandlerLog);
        }
    }


    /**
     * 明确成功的处理逻辑（除更新订单其他业务）
     **/
    public void confirmSuccess(OrderVO payOrder) {

        // 查询查询订单详情
        payOrder = orderApi.orderInfo(payOrder.getId());

        //设置订单状态
        payOrder.setState(PayOrderState.SUCCESS.getCode());

        //TODO 自动分账 处理逻辑， 不影响主订单任务
//        this.updatePayOrderAutoDivision(payOrder);

        //发送商户通知
        PayOrderNotifyDTO payOrderNotifyDTO = new PayOrderNotifyDTO();
        payOrderNotifyDTO.setNotifyUrl(payOrder.getNotifyUrl());
        payOrderNotifyDTO.setOrderType(NotifyType.PAY_SUCCESS.getCode());
        payOrderNotifyDTO.setOrderId(payOrder.getId());
        payOrderNotifyDTO.setMchId(payOrder.getMchId());
        payOrderNotifyDTO.setAppId(payOrder.getAppId());
        notifyApi.payOrderNotify(payOrderNotifyDTO);
    }


    /**
     * @return
     */
    @IgnoreHandlerResponse
    @PostMapping("/{interfaceCode}/{payingAgency}/{payType}")
    public String tongLianPost(@PathVariable("payingAgency") Integer payingAgency,
                               @PathVariable("payType") Integer payType,
                               @PathVariable("interfaceCode") String interfaceCode,
                               HttpServletRequest request) {

        Map<String, String> params = getParams(request);
        String data = JsonUtil.toJson(params);
        log.info("获取到的回调信息：{}", data);
        JsonNode jsonNode = JsonUtil.readTree(data);
        CallbackHandlerLog callbackHandlerLog = new CallbackHandlerLog();
        callbackHandlerLog.setHasHandler(Boolean.FALSE);
        callbackHandlerLog.setPayType(payType);
        callbackHandlerLog.setPayingAgency(payingAgency);
        callbackHandlerLog.setCallbackContext(data);
        callbackHandlerLog.setInterfaceCode(interfaceCode);
        try {
            // 支付流水号
            String trxId = jsonNode.get("trxid").asText();
            callbackHandlerLog.setTrxId(trxId);

            // 收银宝商户号
            String cusid = jsonNode.get("cusid").asText();
            CallbackHandlerLog info = callbackHandlerLogService.getInfo(payingAgency, payType, trxId, interfaceCode);
            if (ObjectUtils.isEmpty(info)) {
                MchInterfaceConfigVO mchInterfaceConfig = payInterfaceApi.mchInterfaceConfig(interfaceCode, cusid);
                // 未处理当前请求或是处理失败 再一次处理请求
                Assert.isNull(mchInterfaceConfig, "当线下码牌商户号:[" + cusid + "]及支付接口：[" + interfaceCode + "]  未配置");
                callbackHandlerLog.setMchNo(mchInterfaceConfig.getMchNo());
                // 预先保存
                // 根据支付接口以及通联收银宝账号获取接口信息
                // 获取商户信息
                MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchInterfaceConfig.getMchId());
                // 获取公钥
                String appPubKey = mchInterfaceConfig.getConfig().get("rsaPublicKey");
                //请求数据验签解密使用公钥
                String signType = params.get("signtype");
                boolean isSign = SybUtil.validSign(params, appPubKey, signType);
                PayingAgency payingAgencyType = PayingAgency.byAgencyCode(payingAgency);
                Assert.isFalse(isSign, ApiException.supplier(NoticeError.PARAMETER_CHECK_ERROR, payingAgencyType.getMsg()));
                // 开始创建订单
                CrCreateOrderDTO crCreateOrderDTO = new CrCreateOrderDTO();
                crCreateOrderDTO.setChannelOrderNo(trxId);
                crCreateOrderDTO.setTradeUser(jsonNode.get("acct").asText());
                crCreateOrderDTO.setMchChannelUser(mchInterfaceConfig.getThirdCode());
                crCreateOrderDTO.setCreateTime(new Date(jsonNode.get("timestamp").asLong()));
                crCreateOrderDTO.setHasDivision(Boolean.FALSE);
                crCreateOrderDTO.setOutTradeNo(jsonNode.get("chnltrxid").asText());
                crCreateOrderDTO.setMchChannelUser(cusid);
                crCreateOrderDTO.setFinishTime(new Date(jsonNode.get("paytime").asLong()));
                crCreateOrderDTO.setMchId(mchInfoVO.getId());
                crCreateOrderDTO.setMchNo(mchInfoVO.getMchNo());
                crCreateOrderDTO.setIsvId(mchInfoVO.getIsvId());
                crCreateOrderDTO.setMchType(mchInfoVO.getType());
                crCreateOrderDTO.setInterfaceCode(interfaceCode);
                crCreateOrderDTO.setMchName(mchInfoVO.getMchName());
                PayWayCode payWayCode = PayWayCode.payWayCode(payType);
                crCreateOrderDTO.setWayCode(payWayCode.getCode());
                crCreateOrderDTO.setMchFeeRate(mchInterfaceConfig.getMchFeeRate());
                crCreateOrderDTO.setTotalAmount(jsonNode.get("amount").asLong());
                crCreateOrderDTO.setPayAmount(jsonNode.get("amount").asLong());
                crCreateOrderDTO.setPromotionAmount(0L);
                crCreateOrderDTO.setChannelResult(data);
                // TODO 应用信息待处理
                crCreateOrderDTO.setState(PayState.findState(jsonNode.get("trxstatus").asText()).getCode());
                Boolean result = orderApi.qCrCreateOrder(crCreateOrderDTO);
                callbackHandlerLog.setHasHandler(result);
            }
            // 接受到推送通知,首先验签
        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
            log.info("回调执行失败:{}", error);
            callbackHandlerLog.setHandlerError(error);
            callbackHandlerLogService.save(callbackHandlerLog);

        }
        return "success";
    }

    private TreeMap<String, String> getParams(HttpServletRequest request) {
        TreeMap<String, String> map = new TreeMap<>();
        Map<String, String[]> reqMap = request.getParameterMap();
        reqMap.forEach((k, v) -> map.put(k, v[0]));
        return map;
    }
}
