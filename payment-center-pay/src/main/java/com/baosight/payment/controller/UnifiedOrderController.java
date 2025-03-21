package com.baosight.payment.controller;

import com.baosight.payment.model.UnifiedOrder;
import com.baosight.payment.service.UnifiedOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 统一下单入口
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UnifiedOrderController {

    private final UnifiedOrderService unifiedOrderService;

    /**
     * 统一下单接口
     **/
    @PostMapping("/api/pay/unifiedOrder")
    public Object unifiedOrder(@RequestBody @Validated UnifiedOrder unifiedOrder) {
//        //获取参数 & 验签
//        UnifiedOrderRQ rq = getRQByWithMchSign(UnifiedOrderRQ.class);
//
//        UnifiedOrderRQ bizRQ = buildBizRQ(rq);
//
//        //实现子类的res
//        ApiRes apiRes = unifiedOrder(bizRQ.getWayCode(), bizRQ);
//        if (apiRes.getData() == null) {
//            return apiRes;
//        }
//
//        UnifiedOrderRS bizRes = (UnifiedOrderRS) apiRes.getData();
//
//        //聚合接口，返回的参数
//        UnifiedOrderRS res = new UnifiedOrderRS();
//        BeanUtils.copyProperties(bizRes, res);
//
//        //只有 订单生成（QR_CASHIER） || 支付中 || 支付成功返回该数据
//        if (bizRes.getOrderState() != null && (bizRes.getOrderState() == PayOrder.STATE_INIT || bizRes.getOrderState() == PayOrder.STATE_ING || bizRes.getOrderState() == PayOrder.STATE_SUCCESS)) {
//            res.setPayDataType(bizRes.buildPayDataType());
//            res.setPayData(bizRes.buildPayData());
//        }
//
//        return ApiRes.okWithSign(res, configContextQueryService.queryMchApp(rq.getMchNo(), rq.getAppId()).getAppSecret());

       return unifiedOrderService.unifiedOrder(unifiedOrder, null);
    }


//    private UnifiedOrderRQ buildBizRQ(UnifiedOrderRQ rq) {
//
//        //支付方式  比如： ali_bar
//        String wayCode = rq.getWayCode();
//
//        //jsapi 收银台聚合支付场景 (不校验是否存在payWayCode)
//        if (CS.PAY_WAY_CODE.QR_CASHIER.equals(wayCode)) {
//            return rq.buildBizRQ();
//        }
//
//        //如果是自动分类条码
//        if (CS.PAY_WAY_CODE.AUTO_BAR.equals(wayCode)) {
//
//            AutoBarOrderRQ bizRQ = (AutoBarOrderRQ) rq.buildBizRQ();
//            wayCode = JeepayKit.getPayWayCodeByBarCode(bizRQ.getAuthCode());
//            rq.setWayCode(wayCode.trim());
//        }
//
//        if (payWayService.count(PayWay.gw().eq(PayWay::getWayCode, wayCode)) <= 0) {
//            throw new BizException("不支持的支付方式");
//        }
//
//        //转换为 bizRQ
//        return rq.buildBizRQ();
//    }
}
