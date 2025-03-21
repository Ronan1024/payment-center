package com.baosight.payment.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.chanel.IPaymentService;
import com.baosight.payment.enums.PayOrderOption;
import com.baosight.payment.error.PayOrderError;
import com.baosight.payment.model.UnifiedOrder;
import com.baosight.payment.pojo.dao.MchAppConfigInfoDAO;
import com.baosight.payment.pojo.entity.PayMchPassage;
import com.baosight.payment.pojo.entity.PayOrder;
//import com.baosight.payment.service.PayOrderService;
import com.baosight.payment.service.UnifiedOrderService;
import com.baosight.payment.utils.StringUtil;
import com.baosight.payment.vo.MchAppInfoVO;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.spring.base.utils.ApplicationContextHolder;
import com.baosight.utils.utils.Assert;
import com.baosight.web.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class UnifiedOrderServiceImpl implements UnifiedOrderService {

//    private final PayOrderService payOrderService;
    //    private final MchInfoApi mchInfoApi;
    private final MchAppConfigApi mchAppApi;
//    private final PayMchPassageService payMchPassageService;

    /**
     * 统一下单
     *
     * @param unifiedOrder 下单请求
     * @param payOrder
     * @return
     */
    @Override
    public Object unifiedOrder(UnifiedOrder unifiedOrder, PayOrder payOrder) {

        // 响应数据
//        UnifiedOrderRS bizRS = null;
        //是否新订单模式   一般接口都为新订单模式 如果已存在则直接更新，否则为插入。
        boolean isNewOrder = payOrder == null;

        try {
            if (payOrder != null) { //当订单存在时，封装公共参数。
                Assert.isFalse(payOrder.equals(PayOrderOption.STATE_INIT), ApiException.supplier(PayOrderError.ORDER_STATUS_ERROR));
//                payOrder.setWayCode(wayCode); // 需要将订单更新 支付方式
//                payOrder.setChannelUser(bizRQ.getChannelUserId()); //更新渠道用户信息
//                bizRQ.setMchNo(payOrder.getMchNo());
//                bizRQ.setAppId(payOrder.getAppId());
//                bizRQ.setMchOrderNo(payOrder.getMchOrderNo());
//                bizRQ.setWayCode(wayCode);
//                bizRQ.setAmount(payOrder.getAmount());
//                bizRQ.setCurrency(payOrder.getCurrency());
//                bizRQ.setClientIp(payOrder.getClientIp());
//                bizRQ.setSubject(payOrder.getSubject());
//                bizRQ.setNotifyUrl(payOrder.getNotifyUrl());
//                bizRQ.setReturnUrl(payOrder.getReturnUrl());
//                bizRQ.setChannelExtra(payOrder.getChannelExtra());
//                bizRQ.setExtParam(payOrder.getExtParam());
//                bizRQ.setDivisionMode(payOrder.getDivisionMode());
            }
            // 商户id
            Long mchNo = unifiedOrder.getMchId();
            // 商户应用信息
            Long appId = unifiedOrder.getAppId();

            // 只有新订单模式，进行校验
            if (isNewOrder) {
//                int orderCount = payOrderService.getPayOrderCount(mchNo, unifiedOrder.getOutTradeNo());
//                Assert.isTrue(orderCount > 0, ApiException.supplier(PayOrderError.ORDER_EXIST_ERROR, unifiedOrder.getOutTradeNo()));
            }

            Assert.isTrue(StringUtils.hasText(unifiedOrder.getNotifyUrl()) && !StringUtil.isAvailableUrl(unifiedOrder.getNotifyUrl()), ApiException.supplier(PayOrderError.NOTIFY_URL_PROTOCOL_ERROR));
            Assert.isTrue(StringUtils.hasText(unifiedOrder.getReturnUrl()) && !StringUtil.isAvailableUrl(unifiedOrder.getReturnUrl()), ApiException.supplier(PayOrderError.RETURN_URL_PROTOCOL_ERROR));
            // TODO 商户信息
//            MchInfoVO mchInfo = mchInfoApi.mchInfo(mchNo);


//            //获取支付参数 (缓存数据) 和 商户信息
//            MchAppConfigContext mchAppConfigContext = configContextQueryService.queryMchInfoAndAppInfo(mchNo, appId);
//            if (mchAppConfigContext == null) {
//                throw new BizException("获取商户应用信息失败");
//            }

            MchAppInfoVO mchApp = mchAppApi.mchApiInfo(mchNo, appId);

//        Assert.isTrue(mchApp == null || mchApp.getState().equals(Boolean.FALSE), ApiException.supplier(PayOrderError.APP_STATE_ERROR, mchApp.getAppName()));

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

            // 根据支付方式， 查询出 该商户 可用的支付接口
//            PayMchPassage mchPayPassage = payMchPassageService.findMchPayPassage(mchNo, appId, unifiedOrder.getWayCode());
//            Assert.isNull(mchPayPassage, ApiException.supplier(PayMchPassageError.MCH_APP_NONSUPPORT_PAY_WAY));

            MchAppConfigInfoDAO mchAppConfigInfoDAO = new MchAppConfigInfoDAO();
            //获取支付接口
//            IPaymentService paymentService = checkMchWayCodeAndGetService(mchPayPassage);
//            Object pay = paymentService.pay(unifiedOrder, mchAppConfigInfoDAO);
//            Long payInterfaceCode = paymentService.getPayInterfaceCode();

            //生成订单
            if (isNewOrder) {
                // TODO 商户信息
//                payOrder = genPayOrder(unifiedOrder, mchInfo, mchApp, payInterfaceCode, mchPayPassage);
            } else {
//                payOrder.setIfCode(payInterfaceCode);
                // 查询支付方式的费率，并 在更新ing时更新费率信息 // TODO 费率待处理
//                payOrder.setMchFeeRate(mchPayPassage.getRate());
//                payOrder.setMchFeeAmount(AmountUtil.calPercentageFee(payOrder.getAmount(), payOrder.getMchFeeRate())); //商户手续费,单位分
            }

            //预先校验
//            ApiException exception = paymentService.preCheck(unifiedOrder, payOrder);
//            Assert.notNull(exception, () -> exception);
// TODO 商户信息
//            String newPayOrderId = paymentService.customPayOrderId(unifiedOrder, payOrder, mchInfo);


            if (isNewOrder) {
//                if (StringUtils.hasText(newPayOrderId)) {
                // 自定义订单号 处理支付订单号
//                    payOrder.setPayOrderId(newPayOrderId);
//                }
                //订单入库 订单状态： 生成状态  此时没有和任何上游渠道产生交互。
//                payOrderService.save(payOrder);
            }
//
//            //调起上游支付接口
//            bizRS = (UnifiedOrderRS) paymentService.pay(bizRQ, payOrder, mchAppConfigContext);
//
//            //处理上游返回数据
//            this.processChannelMsg(bizRS.getChannelRetMsg(), payOrder);
//
//            return packageApiResByPayOrder(bizRQ, bizRS, payOrder);
//
//        } catch (BizException e) {
//            return ApiRes.customFail(e.getMessage());
//
//        } catch (ChannelException e) {
//
//            //处理上游返回数据
//            this.processChannelMsg(e.getChannelRetMsg(), payOrder);
//
//            if (e.getChannelRetMsg().getChannelState() == ChannelRetMsg.ChannelState.SYS_ERROR) {
//                return ApiRes.customFail(e.getMessage());
//            }
//
//            return this.packageApiResByPayOrder(bizRQ, bizRS, payOrder);
//
//            // 响应数据
//            UnifiedOrderRS bizRS = null;
//
//            //是否新订单模式 [  一般接口都为新订单模式，  由于QR_CASHIER支付方式，需要先 在DB插入一个新订单， 导致此处需要特殊判断下。 如果已存在则直接更新，否则为插入。  ]
//            boolean isNewOrder = payOrder == null;
//
//            try {
//
//                if (payOrder != null) { //当订单存在时，封装公共参数。
//
//                    if (payOrder.getState() != PayOrder.STATE_INIT) {
//                        throw new BizException("订单状态异常");
//                    }
//
//                    payOrder.setWayCode(wayCode); // 需要将订单更新 支付方式
//                    payOrder.setChannelUser(bizRQ.getChannelUserId()); //更新渠道用户信息
//                    bizRQ.setMchNo(payOrder.getMchNo());
//                    bizRQ.setAppId(payOrder.getAppId());
//                    bizRQ.setMchOrderNo(payOrder.getMchOrderNo());
//                    bizRQ.setWayCode(wayCode);
//                    bizRQ.setAmount(payOrder.getAmount());
//                    bizRQ.setCurrency(payOrder.getCurrency());
//                    bizRQ.setClientIp(payOrder.getClientIp());
//                    bizRQ.setSubject(payOrder.getSubject());
//                    bizRQ.setNotifyUrl(payOrder.getNotifyUrl());
//                    bizRQ.setReturnUrl(payOrder.getReturnUrl());
//                    bizRQ.setChannelExtra(payOrder.getChannelExtra());
//                    bizRQ.setExtParam(payOrder.getExtParam());
//                    bizRQ.setDivisionMode(payOrder.getDivisionMode());
//                }
//
//                String mchNo = bizRQ.getMchNo();
//                String appId = bizRQ.getAppId();
//
//                // 只有新订单模式，进行校验
//                if (isNewOrder && payOrderService.count(PayOrder.gw().eq(PayOrder::getMchNo, mchNo).eq(PayOrder::getMchOrderNo, bizRQ.getMchOrderNo())) > 0) {
//                    throw new BizException("商户订单[" + bizRQ.getMchOrderNo() + "]已存在");
//                }
//
//                if (StringUtils.isNotEmpty(bizRQ.getNotifyUrl()) && !StringKit.isAvailableUrl(bizRQ.getNotifyUrl())) {
//                    throw new BizException("异步通知地址协议仅支持http:// 或 https:// !");
//                }
//                if (StringUtils.isNotEmpty(bizRQ.getReturnUrl()) && !StringKit.isAvailableUrl(bizRQ.getReturnUrl())) {
//                    throw new BizException("同步通知地址协议仅支持http:// 或 https:// !");
//                }
//
//                //获取支付参数 (缓存数据) 和 商户信息
//                MchAppConfigContext mchAppConfigContext = configContextQueryService.queryMchInfoAndAppInfo(mchNo, appId);
//                if (mchAppConfigContext == null) {
//                    throw new BizException("获取商户应用信息失败");
//                }
//
//                MchInfo mchInfo = mchAppConfigContext.getMchInfo();
//                MchApp mchApp = mchAppConfigContext.getMchApp();
//
//                if (mchApp == null || mchApp.getState() != CS.YES) {
//                    throw new BizException("商户应用状态不可用");
//                }
//
//                //收银台支付并且只有新订单需要走这里，  收银台二次下单的wayCode应该为实际支付方式。
//                if (isNewOrder && CS.PAY_WAY_CODE.QR_CASHIER.equals(wayCode)) {
//
//                    //生成订单
//                    payOrder = genPayOrder(bizRQ, mchInfo, mchApp, null, null);
//                    String payOrderId = payOrder.getPayOrderId();
//                    //订单入库 订单状态： 生成状态  此时没有和任何上游渠道产生交互。
//                    payOrderService.save(payOrder);
//
//                    QrCashierOrderRS qrCashierOrderRS = new QrCashierOrderRS();
//                    QrCashierOrderRQ qrCashierOrderRQ = (QrCashierOrderRQ) bizRQ;
//
//                    DBApplicationConfig dbApplicationConfig = sysConfigService.getDBApplicationConfig();
//
//                    String payUrl = dbApplicationConfig.genUniJsapiPayUrl(QRCodeParams.TYPE_PAY_ORDER, payOrderId);
//                    if (CS.PAY_DATA_TYPE.CODE_IMG_URL.equals(qrCashierOrderRQ.getPayDataType())) { //二维码地址
//                        qrCashierOrderRS.setCodeImgUrl(dbApplicationConfig.genScanImgUrl(payUrl));
//
//                    } else { //默认都为跳转地址方式
//                        qrCashierOrderRS.setPayUrl(payUrl);
//                    }
//
//                    return packageApiResByPayOrder(bizRQ, qrCashierOrderRS, payOrder);
//                }
//
//                // 根据支付方式， 查询出 该商户 可用的支付接口
//                MchPayPassage mchPayPassage = mchPayPassageService.findMchPayPassage(mchAppConfigContext.getMchNo(), mchAppConfigContext.getAppId(), wayCode);
//                if (mchPayPassage == null) {
//                    throw new BizException("商户应用不支持该支付方式");
//                }
//
//                //获取支付接口
//                IPaymentService paymentService = checkMchWayCodeAndGetService(mchAppConfigContext, mchPayPassage);
//                String ifCode = paymentService.getIfCode();
//
//                //生成订单
//                if (isNewOrder) {
//                    payOrder = genPayOrder(bizRQ, mchInfo, mchApp, ifCode, mchPayPassage);
//                } else {
//                    payOrder.setIfCode(ifCode);
//
//                    // 查询支付方式的费率，并 在更新ing时更新费率信息
//                    payOrder.setMchFeeRate(mchPayPassage.getRate());
//                    payOrder.setMchFeeAmount(AmountUtil.calPercentageFee(payOrder.getAmount(), payOrder.getMchFeeRate())); //商户手续费,单位分
//                }
//
//                //预先校验
//                String errMsg = paymentService.preCheck(bizRQ, payOrder);
//                if (StringUtils.isNotEmpty(errMsg)) {
//                    throw new BizException(errMsg);
//                }
//
//                String newPayOrderId = paymentService.customPayOrderId(bizRQ, payOrder, mchAppConfigContext);
//
//
//                if (isNewOrder) {
//                    if (StringUtils.isNotBlank(newPayOrderId)) { // 自定义订单号
//                        payOrder.setPayOrderId(newPayOrderId);
//                    }
//                    //订单入库 订单状态： 生成状态  此时没有和任何上游渠道产生交互。
//                    payOrderService.save(payOrder);
//                }
//
//                //调起上游支付接口
//                bizRS = (UnifiedOrderRS) paymentService.pay(bizRQ, payOrder, mchAppConfigContext);
//
//                //处理上游返回数据
//                this.processChannelMsg(bizRS.getChannelRetMsg(), payOrder);
//
//                return packageApiResByPayOrder(bizRQ, bizRS, payOrder);
//
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

        return null;
    }


    private PayOrder genPayOrder(UnifiedOrder unifiedOrder, MchInfoVO mchInfo, MchAppInfoVO mchApp, Long interfaceCode, PayMchPassage payMchPassage) {
        PayOrder payOrder = new PayOrder();
        //生成订单ID
        payOrder.setMchNo(mchInfo.getId()); //商户号
        //商户名称（简称）
        payOrder.setMchName(mchInfo.getMchName());
        //商户类型
        payOrder.setMchType(mchInfo.getType());
        //商户订单号
        payOrder.setMchOrderNo(unifiedOrder.getOutTradeNo());
        //商户应用appId
        payOrder.setAppId(mchApp.getId());
        //接口代码
        payOrder.setIfCode(interfaceCode);
        //支付方式
        payOrder.setWayCode(unifiedOrder.getWayCode());
        //订单金额
        payOrder.setAmount(unifiedOrder.getTotalFee());
        //todo 商户手续费费率快照
//        if (mchPayPassage != null) {
//            payOrder.setMchFeeRate(mchPayPassage.getRate()); //商户手续费费率快照
//        } else {
//            payOrder.setMchFeeRate(BigDecimal.ZERO.longValue()); //预下单模式， 按照0计算入库， 后续进行更新
//        }
        payOrder.setMchFeeRate(BigDecimal.ZERO.longValue()); //预下单模式， 按照0计算入库， 后续进行更新

        // TODO 手续费待处理
//        payOrder.setMchFeeAmount(AmountUtil.calPercentageFee(payOrder.getAmount(), payOrder.getMchFeeRate())); //商户手续费,单位分

        payOrder.setCurrency(unifiedOrder.getCurrency()); //币种
        payOrder.setState(PayOrderOption.STATE_INIT.getCode()); //订单状态, 默认订单生成状态
        //客户端IP//TODO 客户端ip 待控制
//        payOrder.setClientIp(StringUtils.defaultIfEmpty(rq.getClientIp(), getClientIp()));
        //商品标题
        payOrder.setSubject(unifiedOrder.getSubject());
        //商品描述信息
        payOrder.setBody(unifiedOrder.getBody());
//        payOrder.setChannelExtra(rq.getChannelExtra()); //特殊渠道发起的附件额外参数,  是否应该删除该字段了？？ 比如authCode不应该记录， 只是在传输阶段存在的吧？  之前的为了在payOrder对象需要传参。
        //渠道用户标志 //TODO 渠道标识待处理
//        payOrder.setChannelUser(rq.getChannelUserId());
        //商户扩展参数
        payOrder.setExtParam(unifiedOrder.getExtParam());
        //异步通知地址
        payOrder.setNotifyUrl(unifiedOrder.getNotifyUrl());
        //页面跳转地址
        payOrder.setReturnUrl(unifiedOrder.getReturnUrl());

        // 分账模式 //TODO 分账模式待处理
//        payOrder.setDivisionMode(ObjectUtils.defaultIfNull(rq.getDivisionMode(), PayOrderOption.DIVISION_MODE_FORBID));
        payOrder.setDivisionMode(unifiedOrder.getDivisionMode());
        Date nowDate = new Date();
        //订单过期时间 单位： 秒
        if (unifiedOrder.getExpiredTime() != null) {
            payOrder.setExpiredTime(DateUtil.offsetSecond(nowDate, unifiedOrder.getExpiredTime()));
        } else {
            payOrder.setExpiredTime(DateUtil.offsetHour(nowDate, 2)); //订单过期时间 默认两个小时
        }
        //订单创建时间
        payOrder.setCreateTime(nowDate);
        return payOrder;
    }


    /**
     * 校验： 商户的支付方式是否可用
     * 返回： 支付接口
     **/
    private IPaymentService checkMchWayCodeAndGetService(PayMchPassage mchPayPassage) {
        // 接口代码
        IPaymentService paymentService = ApplicationContextHolder.getBean(mchPayPassage.getPayWayCode(), IPaymentService.class);
        Assert.isNull(paymentService, ApiException.supplier(PayOrderError.PAYMENT_CHANNEL_UNAVAILABLE));
        // 处理支付方式
        Assert.isFalse(paymentService.isSupport(mchPayPassage.getPayWayCode()), ApiException.supplier(PayOrderError.PAY_WAY_NOT_SUPPORT));
        // TODO 支付配置待处理
//
//        if (mchAppConfigContext.getMchType() == MchInfo.TYPE_NORMAL) { //普通商户
//
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
//        }

        return paymentService;
    }


//    /**
//     * 处理返回的渠道信息，并更新订单状态
//     * payOrder将对部分信息进行 赋值操作。
//     **/
//    private void processChannelMsg(ChannelRetMsg channelRetMsg, PayOrder payOrder) {
//
//        //对象为空 || 上游返回状态为空， 则无需操作
//        if (channelRetMsg == null || channelRetMsg.getChannelState() == null) {
//            return;
//        }
//
//        String payOrderId = payOrder.getPayOrderId();
//
//        //明确成功
//        if (ChannelRetMsg.ChannelState.CONFIRM_SUCCESS == channelRetMsg.getChannelState()) {
//
//            this.updateInitOrderStateThrowException(PayOrder.STATE_SUCCESS, payOrder, channelRetMsg);
//
//            //订单支付成功，其他业务逻辑
//            payOrderProcessService.confirmSuccess(payOrder);
//
//            //明确失败
//        } else if (ChannelRetMsg.ChannelState.CONFIRM_FAIL == channelRetMsg.getChannelState()) {
//
//            this.updateInitOrderStateThrowException(PayOrder.STATE_FAIL, payOrder, channelRetMsg);
//
//            // 上游处理中 || 未知 || 上游接口返回异常  订单为支付中状态
//        } else if (ChannelRetMsg.ChannelState.WAITING == channelRetMsg.getChannelState() ||
//                ChannelRetMsg.ChannelState.UNKNOWN == channelRetMsg.getChannelState() ||
//                ChannelRetMsg.ChannelState.API_RET_ERROR == channelRetMsg.getChannelState()
//
//        ) {
//            this.updateInitOrderStateThrowException(PayOrder.STATE_ING, payOrder, channelRetMsg);
//
//            // 系统异常：  订单不再处理。  为： 生成状态
//        } else if (ChannelRetMsg.ChannelState.SYS_ERROR == channelRetMsg.getChannelState()) {
//
//        } else {
//
//            throw new BizException("ChannelState 返回异常！");
//        }
//
//        //判断是否需要轮询查单
//        if (channelRetMsg.isNeedQuery()) {
//            mqSender.send(PayOrderReissueMQ.build(payOrderId, 1), 5);
//        }
//
//    }
//
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
//
//    /**
//     * 统一封装订单数据
//     **/
//    private ApiRes packageApiResByPayOrder(UnifiedOrderRQ bizRQ, UnifiedOrderRS bizRS, PayOrder payOrder) {
//
//        // 返回接口数据
//        bizRS.setPayOrderId(payOrder.getPayOrderId());
//        bizRS.setOrderState(payOrder.getState());
//        bizRS.setMchOrderNo(payOrder.getMchOrderNo());
//
//        if (payOrder.getState() == PayOrder.STATE_FAIL) {
//            bizRS.setErrCode(bizRS.getChannelRetMsg() != null ? bizRS.getChannelRetMsg().getChannelErrCode() : null);
//            bizRS.setErrMsg(bizRS.getChannelRetMsg() != null ? bizRS.getChannelRetMsg().getChannelErrMsg() : null);
//        }
//
//        return ApiRes.okWithSign(bizRS, configContextQueryService.queryMchApp(bizRQ.getMchNo(), bizRQ.getAppId()).getAppSecret());
//    }


}
