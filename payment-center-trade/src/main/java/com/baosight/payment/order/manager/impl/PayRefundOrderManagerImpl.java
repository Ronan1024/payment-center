package com.baosight.payment.order.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.enums.RefundOrderState;
import com.baosight.payment.order.api.dto.CreateRefundOrderDTO;
import com.baosight.payment.order.api.dto.UpdateRefundOrderState;
import com.baosight.payment.order.api.vo.CreateRefundOrderVO;
import com.baosight.payment.order.api.vo.PayRefundOrderVO;
import com.baosight.payment.order.manager.PayRefundOrderManager;
import com.baosight.payment.order.mapper.PayOrderMapper;
import com.baosight.payment.order.mapper.PayRefundOrderMapper;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baosight.payment.order.pojo.entity.PayRefundOrder;
import com.baosight.payment.utils.IdGenUtil;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Manager
@RequiredArgsConstructor
public class PayRefundOrderManagerImpl implements PayRefundOrderManager {
    private final PayRefundOrderMapper payRefundOrderMapper;
    private final PayOrderMapper payOrderMapper;

    /**
     * 获取指定商户指定的退款订单数量
     *
     * @param mchId       商户id
     * @param mchRefundNo 商户退款订单号
     * @param refundState 退款状态  如果为空则不携带参数查询
     */
    @Override
    public Long refundOrderCount(Long mchId, String mchRefundNo, Integer refundState) {
        return payRefundOrderMapper.selectCount(new LambdaQueryWrapper<PayRefundOrder>()
                .eq(PayRefundOrder::getMchRefundNo, mchRefundNo)
                .eq(!ObjectUtils.isEmpty(refundState), PayRefundOrder::getState, refundState)
                .eq(PayRefundOrder::getMchId, mchId)
        );
    }

    /**
     * 获取指定类型全部退款金额
     *
     * @param payOrderId  支付订单ID
     * @param refundState 退款状态
     */
    @Override
    public Long sumRefundAmount(Long payOrderId, Integer refundState) {
        List<PayRefundOrder> refundOrder = payRefundOrderMapper.selectList(new LambdaQueryWrapper<PayRefundOrder>()
                .select(PayRefundOrder::getRefundAmount)
                .eq(PayRefundOrder::getPayOrderId, payOrderId)
                .eq(PayRefundOrder::getState, refundState)
        );
        return refundOrder.stream().map(PayRefundOrder::getRefundAmount).reduce(Long::sum).orElse(0L);
    }

    /**
     * 创建退款订单
     *
     * @param refundOrder 退款订单请求体
     * @return 退款订单创建结果
     */
    @Override
    public CreateRefundOrderVO createRefundOrder(CreateRefundOrderDTO refundOrder) {
        Assert.isNull(refundOrder, "创建退款订单不能请求参数不能为空");
        if (ObjectUtils.isEmpty(refundOrder.getCreateTime())) {
            refundOrder.setCreateTime(new Date());
        }
        String refundNo = IdGenUtil.generateId(refundOrder.getMchId());
        PayRefundOrder payRefundOrder = PayReFundConvert.INSTANCE.toPayRefundOrder(refundOrder);
        payRefundOrder.setRefundNo(refundNo);
        boolean insert = payRefundOrderMapper.insert(payRefundOrder) > 0;
        if (insert) {
            PayOrder payOrder = payOrderMapper.selectById(payRefundOrder.getPayOrderId());
            payOrder.setRefundTimes(payOrder.getRefundTimes() + 1);
            payOrder.setRefundAmount(payOrder.getRefundAmount() + refundOrder.getRefundAmount());
            if (payOrder.getRefundAmount().equals(payOrder.getPayAmount())) {
                payOrder.setRefundState(2);
                payOrder.setHasDivision(Boolean.FALSE);
            } else {
                // 部分退款
                payOrder.setRefundState(1);
            }

            // 修改退款金额
            payOrderMapper.updateById(payOrder);
        }

        CreateRefundOrderVO result = new CreateRefundOrderVO();
        result.setRefundOrderId(payRefundOrder.getId());
        result.setRefundOrderNo(payRefundOrder.getRefundNo());
        return result;
    }

    /**
     * 更新退款订单内容
     *
     * @param updateRefundOrderState 待更新请求体
     */
    @Override
    public Boolean updateInitOrderStateThrowException(UpdateRefundOrderState updateRefundOrderState) {
        PayRefundOrder payRefundOrder = payRefundOrderMapper.selectById(updateRefundOrderState.getRefundId());
        if (ObjectUtils.isEmpty(payRefundOrder)) {
            return Boolean.FALSE;
        }
        // TODO 处理发起调用 与 回调返回 等信息的记录
        payRefundOrder.setState(updateRefundOrderState.getRefundState());
        payRefundOrder.setChannelOrderNo(updateRefundOrderState.getChannelOrderNo());
        payRefundOrder.setErrCode(updateRefundOrderState.getErrCode());
        payRefundOrder.setErrMsg(updateRefundOrderState.getErrMsg());
        payRefundOrder.setChanelResult(updateRefundOrderState.getChanelResult());
        payRefundOrder.setSuccessTime(updateRefundOrderState.getFinishTime());
        boolean result = payRefundOrderMapper.updateById(payRefundOrder) > 0;
        if (result) {
            if (updateRefundOrderState.getRefundState().equals(RefundOrderState.REFUND_FAILED.code())) {
                PayOrder payOrder = payOrderMapper.selectById(payRefundOrder.getPayOrderId());
                payOrder.setRefundTimes(payOrder.getRefundTimes() - 1);
                payOrder.setRefundAmount(payOrder.getRefundAmount() - payRefundOrder.getRefundAmount());
                if (payOrder.getRefundAmount().equals(payOrder.getPayAmount())) {
                    payOrder.setRefundState(2);
                } else {
                    // 部分退款
                    payOrder.setHasDivision(Boolean.TRUE);
                    payOrder.setRefundState(1);
                }
            }
        }
        return result;
    }

    /**
     * 获取支付退款订单信息
     *
     * @param refundOrderId 退款订单id
     */
    @Override
    public PayRefundOrderVO refundOrderInfo(Long refundOrderId) {
        PayRefundOrder payRefundOrder = payRefundOrderMapper.selectById(refundOrderId);
        if (ObjectUtils.isEmpty(payRefundOrder)) {
            return null;
        }
        return PayReFundConvert.INSTANCE.toPayRefundOrderVO(payRefundOrder);
    }

    /**
     * 更新通知状态
     *
     * @param refundOrderId 退款订单id
     * @param state         通知状态
     */
    @Override
    public Boolean updateNotifySent(Long refundOrderId, Integer state) {
        PayRefundOrder payRefundOrder = payRefundOrderMapper.selectById(refundOrderId);
        if (!ObjectUtils.isEmpty(payRefundOrder)) {
            if (!ObjectUtils.isEmpty(state)) {
                payRefundOrder.setState(state);
            }
            return payRefundOrderMapper.updateById(payRefundOrder) > 0;
        }
        return Boolean.FALSE;
    }
}
