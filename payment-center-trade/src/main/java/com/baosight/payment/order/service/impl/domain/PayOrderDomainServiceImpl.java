package com.baosight.payment.order.service.impl.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.order.error.PayOrderError;
import com.baosight.payment.order.mapper.PayOrderMapper;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.service.domain.PayOrderDomainService;
import com.baosight.utils.utils.Assert;
import com.baosight.web.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author longjiangran
 * @description 针对表【pay_order(支付订单表)】的数据库操作Service实现
 * @createDate 2025-03-19 14:19:14
 */
@Service
@RequiredArgsConstructor
public class PayOrderDomainServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements PayOrderDomainService {
    private final PayOrderMapper payOrderMapper;

    /**
     * 修改订单状态为已通知
     *
     * @param orderId      订单id
     * @param notifyStatus 异步通知状态
     */
    @Override
    public Boolean updateNotifySent(Long orderId, Integer notifyStatus) {
        return updateNotifySent(orderId, notifyStatus, null);
    }

    /**
     * 修改订单状态为已通知
     *
     * @param orderId      订单id
     * @param notifyStatus 异步通知状态
     * @param notifyUrl    通知地址
     */
    @Override
    public Boolean updateNotifySent(Long orderId, Integer notifyStatus, String notifyUrl) {
        PayOrder payOrder = payOrderMapper.selectById(orderId);
        Assert.isNull(payOrder, ApiException.supplier(PayOrderError.ORDER_NOT_FOUND));
        return payOrderMapper.update(new LambdaUpdateWrapper<PayOrder>()
                .eq(PayOrder::getId, orderId)
                .set(PayOrder::getNotifyState, notifyStatus)
                .set(StringUtils.hasText(notifyUrl), PayOrder::getNotifyUrl, notifyUrl)
        ) > 0;
    }

    /**
     * 获取当前商家指定订单数量
     *
     * @param mchId      商家号
     * @param outTradeNo 商户订单号
     */
    @Override
    public int getPayOrderCount(Long mchId, String outTradeNo) {
        return Math.toIntExact(payOrderMapper.selectCount(new LambdaQueryWrapper<PayOrder>()
                .eq(PayOrder::getMchId, mchId)
                .eq(PayOrder::getMchOrderNo, outTradeNo)));
    }


}




