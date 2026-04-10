package com.baosight.payment.system.service;

import com.baosight.payment.system.pojo.dto.req.ClientChannelConfigReqDTO;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelConfigRespDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelRespDTO;

import java.util.List;
import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
public interface SystemMchChannelConfigService {

    /**
     * 保存商户支付渠道权限
     *
     * @param mchChannelPermission 商户支付渠道权限
     */
    Boolean saveClientChannelPermission(MchChannelPermissionReqDTO mchChannelPermission);

    /**
     * 获取商户已授权的支付渠道
     *
     * @param type  商户类型
     * @param mchId 商户ID
     */
    List<String> getMchChannel(Integer type, Long mchId);

    /**
     * 获取当前商户渠道配置列表
     *
     * @param clientId 商户id
     */
    List<ClientChannelRespDTO> mchChannelList(Long clientId);

    /**
     * 保存商户渠道配置信息
     *
     * @param channelConfigReq 渠道配置信息请求参数
     */
    String saveClientChannelConfig(ClientChannelConfigReqDTO channelConfigReq);

    /**
     * 获取客户端支付渠道配置信息
     * @param channelId 支付渠道id
     * @param clientId 客户端id
     * @param type 客户端类型
     */
    ClientChannelConfigRespDTO clientChannelConfigInfo(Long channelId, Long clientId, Integer type);

    /**
     * 执行支付渠道处理
     *
     * @param clientId    客户端id
     * @param channelCode
     * @param body
     * @return 是否成功
     */
    Boolean executeChannelProcess(Long clientId, String channelCode, Object body);

    /**
     * 获取商户支付渠道配置信息
     * @param mchId 商户id
     * @param channelCode 支付渠道编码
     * @return 支付渠道配置信息
     */
    Map<String, String> mchChannelConfig(Long mchId, String channelCode);
}
