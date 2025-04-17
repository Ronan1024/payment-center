package com.baosight.payment.order.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.enums.DivisionState;
import com.baosight.payment.enums.PayOrderState;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.order.convert.OrderConvert;
import com.baosight.payment.order.convert.PayOrderConvert;
import com.baosight.payment.order.manager.PayOrderManager;
import com.baosight.payment.order.mapper.PayOrderMapper;
import com.baosight.payment.order.pojo.dto.PayOrderPageDTO;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.pojo.vo.PayOrderInfoVO;
import com.baosight.payment.order.pojo.vo.PayOrderPageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Manager
@RequiredArgsConstructor
public class PayOrderManagerImpl implements PayOrderManager {
    private final PayOrderMapper payOrderMapper;

    /**
     * 获取订单信息
     *
     * @param mchId      系统商户id
     * @param mchOrderNo 商户订单号
     * @param payOrderNo 系统订单号
     */
    @Override
    public OrderVO orderInfo(Long mchId, String mchOrderNo, String payOrderNo) {
        PayOrder payOrder = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrder>()
                .eq(PayOrder::getMchId, mchId)
                .eq(PayOrder::getMchOrderNo, mchOrderNo)
                .eq(PayOrder::getOrderNo, payOrderNo));
        return OrderConvert.INSTANCE.toOrderVO(payOrder);
    }

    /**
     * 获取订单列表
     *
     * @param orderId 订单id
     */
    @Override
    public List<OrderVO> orderList(List<Long> orderId) {
        if (CollectionUtils.isEmpty(orderId)) {
            return new ArrayList<>();
        }
        List<PayOrder> payOrderList = payOrderMapper.selectList(new LambdaQueryWrapper<PayOrder>()
                .in(PayOrder::getId, orderId)
        );
        return payOrderList.stream().map(OrderConvert.INSTANCE::toOrderVO).toList();
    }

    /**
     * 更新订单对账状态
     *
     * @param orderIdList 订单id列表
     */
    @Override
    public Boolean updateOrderCheckState(List<Long> orderIdList) {
        int update = payOrderMapper.update(new LambdaUpdateWrapper<PayOrder>()
                .in(PayOrder::getId, orderIdList)
                .set(PayOrder::getState, PayOrderState.SUCCESS.getCode())
                .set(PayOrder::getDivisionState, DivisionState.WAITING.getCode())
        );
        return update == orderIdList.size();
    }

    /**
     * 支付订单分页列表
     *
     * @param payOrderPage 支付订单page
     */
    @Override
    public PageResponse<PayOrderPageVO> payOrderPage(PayOrderPageDTO payOrderPage) {
        PageUtil<PayOrderPageVO> pageUtil = new PageUtil<>(payOrderPage);
        return pageUtil.builder(payOrderMapper.payOrderPage(pageUtil.Page(), payOrderPage)).build();
    }

    /**
     * 支付订单详情
     * @param id id
     */
    @Override
    public PayOrderInfoVO info(Long id) {
        PayOrder payOrder = payOrderMapper.selectById(id);
        return PayOrderConvert.INSTANCE.toPayOrderInfoVO(payOrder);
    }


}
