package com.baosight.payment.chanel.ums.payway;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.chanel.IPaymentService;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.enums.PayWayCode;
import com.baosight.payment.model.order.UnifiedOrder;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import com.baosight.payment.vo.MchInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.baosight.payment.constant.PayWay.UMS_WX_MINI_PROGRAM;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/29
 */
@Slf4j
@RequiredArgsConstructor
@Component(value = UMS_WX_MINI_PROGRAM)
public class WxMiniProgram implements IPaymentService {
    /**
     * 获取到接口code
     **/
    @Override
    public String getPayInterfaceCode() {
        return PayInterfaceCode.UMS.getCode();
    }

    /**
     * 调起支付接口并响应数据
     *
     * @param unifiedOrder 申请支付请求体
     * @param mchInfo      商户信息
     * @param createOrder
     */
    @Override
    public OrderChannelHandlerResult pay(UnifiedOrder unifiedOrder, MchInfoVO mchInfo, CreateOrderDTO createOrder) {
        return null;
    }

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     *
     * @param unifiedOrder
     * @param payOrder
     */
    @Override
    public ServiceException preCheck(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder) {
        return null;
    }

    /**
     * 自定义支付订单号， 若返回空则使用系统生成订单号
     *
     * @param unifiedOrder
     * @param payOrder
     * @param mchInfoVO
     */
    @Override
    public String customPayOrderId(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder, MchInfoVO mchInfoVO) {
        return "";
    }

    /**
     * 获取到的支付方式code
     */
    @Override
    public String payWayCode() {
        return PayWayCode.UMS_WX_MINI_PROGRAM.getCode();
    }

    /**
     * 是否支持当前支付方式
     *
     * @param patWayCode 支付方式code
     */
    @Override
    public boolean isSupport(String patWayCode) {
        return false;
    }
}
