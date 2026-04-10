package com.baosight.payment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.system.dao.entity.SystemChannelFlowDefine;
import com.baosight.payment.system.pojo.dto.req.SavePaymentChannelFlowReqDTO;
import com.baosight.payment.system.pojo.dto.resp.PaymentChannelFlowRespDTO;

import java.util.List;
import java.util.Map;

/**
 * @author longjiangran
 * @description 针对表【payment_channel_flow(支付渠道流程管理)】的数据库操作Service
 * @createDate 2026-03-26 15:12:47
 */
public interface SystemChannelFlowDefineService extends IService<SystemChannelFlowDefine> {

    /**
     * 获取支付渠道流程列表
     *
     */
    List<PaymentChannelFlowRespDTO> list(String channelCode);



    /**
     * 新增支付渠道流程
     *
     * @param saveDTO 新增DTO
     * @return 是否成功
     */
    Boolean add(SavePaymentChannelFlowReqDTO saveDTO);


    /**
     * 删除支付渠道流程
     *
     * @return 是否成功
     */
    Boolean delete(String channelCode, Long id);

    /**
     * 根据渠道编号获取渠道流程类型列表
     * @param channelCode 渠道编号
     */
    Map<String, String> listChannelFlowType(String channelCode);
}
