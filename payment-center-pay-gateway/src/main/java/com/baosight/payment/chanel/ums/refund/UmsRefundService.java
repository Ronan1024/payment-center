package com.baosight.payment.chanel.ums.refund;

import com.baosight.payment.chanel.IRefundService;
import com.baosight.payment.model.refund.RefundOrder;
import com.baosight.payment.order.api.vo.CreateRefundOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.pojo.vo.RefundChannelHandlerResult;
import com.baosight.payment.vo.MchInfoVO;
import org.springframework.stereotype.Component;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/31
 */
@Component("UMSRefund")
public class UmsRefundService implements IRefundService {
    /**
     * 获取到接口code
     **/
    @Override
    public String getInterfaceCode() {
        return "";
    }

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     *
     * @param refundOrder
     * @param payOrder
     */
    @Override
    public String preCheck(RefundOrder refundOrder, OrderVO payOrder) {
        return "";
    }

    /**
     * 调起退款接口，并响应数据；  内部处理普通商户和服务商模式
     *
     * @param refundOrder
     * @param createRefundOrderVO
     * @param payOrder
     * @param mchInfo
     */
    @Override
    public RefundChannelHandlerResult refund(RefundOrder refundOrder, CreateRefundOrderVO createRefundOrderVO, OrderVO payOrder, MchInfoVO mchInfo) throws Exception {
        return null;
    }

    /**
     * 退款查单接口
     *
     * @param refundOrder
     * @param mchInfo
     */
    @Override
    public RefundChannelHandlerResult query(RefundOrder refundOrder, MchInfoVO mchInfo) throws Exception {
        return null;
    }
}
