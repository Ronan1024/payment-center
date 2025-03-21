//package com.baosight.payment.service;
//
//import com.baosight.payment.pojo.entity.PayOrder;
//import com.baomidou.mybatisplus.extension.service.IService;
//
///**
// * @author longjiangran
// * @description 针对表【pay_order(支付订单表)】的数据库操作Service
// * @createDate 2025-02-24 16:41:43
// */
//public interface PayOrderService extends IService<PayOrder> {
//
//    /**
//     * 获取支付订单信息
//     *
//     * @param mchId      商户号
//     * @param mchOrderId 商户订单id
//     * @param mchType    商户类型
//     */
//    PayOrder getPayOrder(String mchId, String mchOrderId, Integer mchType);
//
//    /**
//     * 获取支付订单数据
//     *
//     * @param mchId      商户号
//     * @param mchOrderId 商户订单id
//     */
//    int getPayOrderCount(Long mchId, String mchOrderId);
//}
