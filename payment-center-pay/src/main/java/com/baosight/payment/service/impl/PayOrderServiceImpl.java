//package com.baosight.payment.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baosight.payment.pojo.entity.PayOrder;
//import com.baosight.payment.service.PayOrderService;
//import com.baosight.payment.mapper.PayOrderMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
///**
// * @author longjiangran
// * @description 针对表【pay_order(支付订单表)】的数据库操作Service实现
// * @createDate 2025-02-24 16:41:43
// */
//@Service
//@RequiredArgsConstructor
//public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {
//    private final PayOrderMapper payOrderMapper;
//
//    /**
//     * 获取支付订单信息
//     *
//     * @param mchId      商户号
//     * @param mchOrderId 商户订单id
//     * @param mchType    商户类型
//     */
//    @Override
//    public PayOrder getPayOrder(String mchId, String mchOrderId, Integer mchType) {
//        return null;
//    }
//
//    /**
//     * 获取支付订单数据
//     *
//     * @param mchId      商户号
//     * @param mchOrderId 商户订单id
//     */
//    @Override
//    public int getPayOrderCount(Long mchId, String mchOrderId) {
//        Long count = payOrderMapper.selectCount(new LambdaQueryWrapper<PayOrder>()
//                .eq(PayOrder::getMchNo, mchId)
//                .eq(PayOrder::getMchOrderNo, mchOrderId)
//        );
//        return Math.toIntExact(count);
//    }
//}
//
//
//
//
