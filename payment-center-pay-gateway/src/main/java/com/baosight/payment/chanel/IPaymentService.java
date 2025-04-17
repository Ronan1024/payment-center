package com.baosight.payment.chanel;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.model.order.UnifiedOrder;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import com.baosight.payment.vo.MchInfoVO;

public interface IPaymentService {

    /**
     * 获取到接口code
     **/
    String getPayInterfaceCode();

    /**
     * 调起支付接口并响应数据
     *
     * @param unifiedOrder 申请支付请求体
     * @param mchInfo      商户信息
     * @param createOrder
     */
    OrderChannelHandlerResult pay(UnifiedOrder unifiedOrder, MchInfoVO mchInfo, CreateOrderDTO createOrder);

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     */
    ServiceException preCheck(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder);


    /**
     * 自定义支付订单号， 若返回空则使用系统生成订单号
     */
    String customPayOrderId(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder, MchInfoVO mchInfoVO);

    /**
     * 获取到的支付方式code
     */
    String payWayCode();

    /**
     * 是否支持当前支付方式
     *
     * @param patWayCode 支付方式code
     */
    boolean isSupport(String patWayCode);
}
