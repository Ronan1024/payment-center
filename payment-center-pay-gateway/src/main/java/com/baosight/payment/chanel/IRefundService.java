package com.baosight.payment.chanel;

import com.baosight.payment.model.refund.RefundOrder;
import com.baosight.payment.order.api.vo.CreateRefundOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.pojo.vo.RefundChannelHandlerResult;
import com.baosight.payment.vo.MchInfoVO;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
public interface IRefundService {

    /**
     * 获取到接口code
     **/
    String getInterfaceCode();

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     */
    String preCheck(RefundOrder refundOrder, OrderVO payOrder);

    /**
     * 调起退款接口，并响应数据；  内部处理普通商户和服务商模式
     **/
    RefundChannelHandlerResult refund(RefundOrder refundOrder, CreateRefundOrderVO createRefundOrderVO, OrderVO payOrder, MchInfoVO mchInfo) throws Exception;

    /**
     * 退款查单接口
     **/
    RefundChannelHandlerResult query(RefundOrder refundOrder, MchInfoVO mchInfo) throws Exception;



}
