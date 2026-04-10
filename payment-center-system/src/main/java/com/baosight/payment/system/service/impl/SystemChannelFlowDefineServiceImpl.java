package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.channel.api.ChannelFlowAPi;
import com.baosight.payment.channel.api.ChannelInfoApi;
import com.baosight.payment.channel.dto.resp.ChannelInfoRespDTO;
import com.baosight.payment.system.convert.SystemChannelFlowDefineConvert;
import com.baosight.payment.system.dao.entity.SystemChannelFlowDefine;
import com.baosight.payment.system.dao.manager.SystemChannelFlowDefineManager;
import com.baosight.payment.system.dao.mapper.SystemChannelFlowDefineMapper;
import com.baosight.payment.system.pojo.dto.req.SavePaymentChannelFlowReqDTO;
import com.baosight.payment.system.pojo.dto.resp.PaymentChannelFlowRespDTO;
import com.baosight.payment.system.service.SystemChannelFlowDefineService;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.baosight.payment.system.error.PaymentChannelFlowError.*;

/**
 * @author longjiangran
 * @description 针对表【payment_channel_flow(支付渠道流程管理)】的数据库操作Service实现
 * @createDate 2026-03-26 15:12:47
 */
@Service
@RequiredArgsConstructor
public class SystemChannelFlowDefineServiceImpl extends ServiceImpl<SystemChannelFlowDefineMapper, SystemChannelFlowDefine> implements SystemChannelFlowDefineService {

    private final SystemChannelFlowDefineManager paymentChannelFlowManager;
    private final ChannelInfoApi channelInfoApi;
    private final ChannelFlowAPi channelFlowAPi;

    /**
     * 获取支付渠道流程列表
     *
     */
    @Override
    public List<PaymentChannelFlowRespDTO> list(String channelCode) {
        List<SystemChannelFlowDefine> list = paymentChannelFlowManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getChannelCode, channelCode)
                .orderByAsc(SystemChannelFlowDefine::getStepOrder).list();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(SystemChannelFlowDefineConvert.INSTANCE::toPaymentChannelFlowRespDTO).toList();
    }


    /**
     * 新增支付渠道流程
     *
     * @param saveDTO 新增DTO
     * @return 是否成功
     */
    @Override
    public Boolean add(SavePaymentChannelFlowReqDTO saveDTO) {
        ChannelInfoRespDTO info = channelInfoApi.info(saveDTO.getChannelCode());
        SystemChannelFlowDefine paymentChannelFlow = paymentChannelFlowManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getStepType, saveDTO.getStepType()).one();
        Assert.notNull(paymentChannelFlow, ApiException.supplier(PAYMENT_TYPE_ALREADY_USED));
        SystemChannelFlowDefine entity = SystemChannelFlowDefineConvert.INSTANCE.toEntity(saveDTO);
        entity.setChannelId(info.getId());
        List<SystemChannelFlowDefine> list = paymentChannelFlowManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getChannelCode, saveDTO.getChannelCode())
                .list();

        // 处理前后边界问题
        if (CollectionUtils.isEmpty(list)) {
            Assert.isFalse(saveDTO.getStepOrder().equals(1), ApiException.supplier(PAYMENT_CHANNEL_FLOW_STEP_ORDER_INVALID));
        } else {
            Map<Integer, SystemChannelFlowDefine> flowDefineMap = list.stream().collect(Collectors.toMap(SystemChannelFlowDefine::getStepOrder, e -> e));
            Assert.isTrue(flowDefineMap.containsKey(saveDTO.getStepOrder()), ApiException.supplier(PAYMENT_CHANNEL_FLOW_CODE_ALREADY_EXIST));
            Assert.isFalse(flowDefineMap.containsKey(saveDTO.getStepOrder() - 1), ApiException.supplier(PAYMENT_CHANNEL_FLOW_STEP_ORDER_INVALID));
        }
        return paymentChannelFlowManager.save(entity);
    }

    @Override
    public Boolean delete(String channelCode, Long id) {
        SystemChannelFlowDefine paymentChannelFlow = paymentChannelFlowManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getChannelCode, channelCode)
                .eq(SystemChannelFlowDefine::getId, id).one();
        Assert.isNull(paymentChannelFlow, ApiException.supplier(PAYMENT_CHANNEL_FLOW_NOT_EXIST));

        return paymentChannelFlowManager.removeById(id);
    }

    /**
     * 根据渠道编号获取渠道流程类型列表
     *
     * @param channelCode 渠道编号
     */
    @Override
    public Map<String, String> listChannelFlowType(String channelCode) {
        return channelFlowAPi.channelFlowList(channelCode);
    }
}




