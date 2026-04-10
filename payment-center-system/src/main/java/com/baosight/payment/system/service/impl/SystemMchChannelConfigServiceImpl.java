package com.baosight.payment.system.service.impl;

import com.baosight.common.exception.ServiceException;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.convert.PayInterfaceConfigConvert;
import com.baosight.payment.system.convert.SystemChannelFlowDefineConvert;
import com.baosight.payment.system.dao.entity.SystemChannelFlowDefine;
import com.baosight.payment.system.dao.entity.SystemClientChannelPermission;
import com.baosight.payment.system.dao.entity.SystemMchChannelConfigFlow;
import com.baosight.payment.system.dao.manager.*;
import com.baosight.payment.system.pojo.dto.req.ClientChannelConfigReqDTO;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelConfigRespDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelRespDTO;
import com.baosight.payment.system.pojo.dto.resp.MchChannelFlowRespDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.service.SystemMchChannelConfigService;
import com.baosight.payment.system.utils.DynamicFormUtil;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.web.core.exception.ApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ronan.common.json.JsonUtil;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.baosight.payment.system.dao.entity.SystemMchChannelConfigFlow.Status.UNPLAYED;
import static com.baosight.payment.system.error.PayInterfaceConfigError.MERCHANT_HAS_NO_CHANNEL_PERMISSION;
import static com.baosight.payment.system.error.PayInterfaceError.PAY_INTERFACE_NOT_EXIST;
import static com.baosight.payment.system.error.PaymentChannelFlowError.MERCHANT_CHANNEL_FLOW_DATA_EXCEPTION;
import static com.baosight.payment.system.error.PaymentChannelFlowError.MERCHANT_CHANNEL_FLOW_PROCESSING;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/27
 */
@Service
@RequiredArgsConstructor
public class SystemMchChannelConfigServiceImpl implements SystemMchChannelConfigService {

    private final MchInfoApi mchInfoApi;
    private final PayInterfaceConfigManager payInterfaceConfigManager;
    private final PayInterFaceDefineManager payInterFaceDefineManager;
    private final SystemMchChannelConfigFlowManager systemMchChannelConfigFlowManager;
    private final SystemChannelFlowDefineManager systemChannelFlowDefineManager;
    private final SystemClientChannelPermissionManager systemClientChannelPermissionManager;

    /**
     * 保存商户支付渠道权限
     *
     * @param mchChannelPermission 商户支付渠道权限
     */
    @Override
    public Boolean saveClientChannelPermission(MchChannelPermissionReqDTO mchChannelPermission) {
        MchInfoVO mchInfoVO = mchChannelPermission.getClientType().equals(PayClientType.MERCHANT.code()) || mchChannelPermission.getClientType().equals(PayClientType.SUB_MERCHANT.code())
                ? mchInfoApi.mchInfo(mchChannelPermission.getClientId())
                : new MchInfoVO();

        // 特约商户处理是否有权限
        if (mchChannelPermission.getClientType().equals(PayClientType.SUB_MERCHANT.code())) {
            Assert.isNull(mchInfoVO, ApiException.supplier(MERCHANT_HAS_NO_CHANNEL_PERMISSION));
            List<SystemClientChannelPermission> clientChannelPermissions = systemClientChannelPermissionManager.lambdaQuery()
                    .eq(SystemClientChannelPermission::getClientId, mchInfoVO.getIsvId()).list();
            List<Long> list = clientChannelPermissions.stream().map(SystemClientChannelPermission::getChannelDefineId).toList();
            Assert.isFalse(new HashSet<>(list).containsAll(mchChannelPermission.getChannelId()), ApiException.supplier(MERCHANT_HAS_NO_CHANNEL_PERMISSION));
        }

        List<PayInterfaceDefine> interfaceDefineList = payInterFaceDefineManager.lambdaQuery()
                .in(PayInterfaceDefine::getId, mchChannelPermission.getChannelId())
                .eq(mchChannelPermission.getClientType().equals(PayClientType.MERCHANT.code()), PayInterfaceDefine::getHasMch, Boolean.TRUE)
                .eq(mchChannelPermission.getClientType().equals(PayClientType.SUB_MERCHANT.code()) || mchChannelPermission.getClientType().equals(PayClientType.SERVICE_PROVIDER.code()), PayInterfaceDefine::getHasIsvMch, Boolean.TRUE)
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE).list();

        Assert.isFalse(interfaceDefineList.size() == mchChannelPermission.getChannelId().size(), ApiException.supplier(PAY_INTERFACE_NOT_EXIST));

        List<PayInterfaceConfig> configList = payInterfaceConfigManager.lambdaQuery()
                .eq(PayInterfaceConfig::getClientId, mchChannelPermission.getClientId()).eq(PayInterfaceConfig::getClientType, mchChannelPermission.getClientType()).list();

        // 当前商户需要移除的渠道列表
        List<Long> channelId = configList.stream().map(PayInterfaceConfig::getInterfaceId)
                .filter(interfaceId -> !mchChannelPermission.getChannelId().contains(interfaceId)).toList();

        // 当前商户已有的支付渠道列表
        List<Long> mchChannelId = configList.stream().map(PayInterfaceConfig::getInterfaceId).toList();

        List<SystemMchChannelConfigFlow> list = systemMchChannelConfigFlowManager.lambdaQuery()
                .eq(SystemMchChannelConfigFlow::getChannelId, mchChannelPermission.getClientId())
                .eq(SystemMchChannelConfigFlow::getClientType, mchChannelPermission.getClientType())
                .list();
        Map<String, SystemMchChannelConfigFlow> systemMchChannelConfigFlowMap = list.stream().collect(Collectors.toMap(SystemMchChannelConfigFlow::getChannelCode, e -> e));

        // 获取渠道信息
        List<String> channelCodeList = interfaceDefineList.stream().map(PayInterfaceDefine::getCode).toList();


        List<SystemChannelFlowDefine> systemChannelFlowDefineList = systemChannelFlowDefineManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getClientType, mchChannelPermission.getClientType())
                .in(SystemChannelFlowDefine::getChannelCode, channelCodeList).groupBy(SystemChannelFlowDefine::getChannelCode).list();

        Map<String, SystemChannelFlowDefine> systemChannelFlowDefineMap = systemChannelFlowDefineList.stream().collect(Collectors.toMap(SystemChannelFlowDefine::getChannelCode, e -> e));

        // 支付渠道配置
        List<PayInterfaceConfig> payInterfaceConfigList = new ArrayList<>();
        // 支付渠道配置流程
        List<SystemMchChannelConfigFlow> systemMchChannelConfigFlowList = new ArrayList<>();


        // 需要新增加的配置
        interfaceDefineList.stream()
                .filter(e -> !mchChannelId.contains(e.getId()))
                .forEach(e -> {
                    PayInterfaceConfig payInterfaceConfig = new PayInterfaceConfig()
                            .setInterfaceId(e.getId()).setClientId(mchChannelPermission.getClientId()).setClientType(mchChannelPermission.getClientType())
                            .setInterfaceCode(e.getCode()).setEnable(Boolean.FALSE)
                            .setParentClientId(ObjectUtils.isEmpty(mchInfoVO.getIsvId()) ? 0L : mchInfoVO.getIsvId());
                    if (systemChannelFlowDefineMap.containsKey(e.getCode()) && !systemMchChannelConfigFlowMap.containsKey(e.getCode())) {
                        // 处理客户端渠道配置流程如果没有定义则不添加
                        SystemChannelFlowDefine systemChannelFlowDefine = systemChannelFlowDefineMap.get(e.getCode());
                        SystemMchChannelConfigFlow systemMchChannelConfigFlow = new SystemMchChannelConfigFlow()
                                .setClientId(mchChannelPermission.getClientId())
                                .setClientType(mchChannelPermission.getClientType())
                                .setChannelCode(e.getCode())
                                .setStatus(UNPLAYED.code())
                                .setChannelId(systemChannelFlowDefine.getId());
                        systemMchChannelConfigFlowList.add(systemMchChannelConfigFlow);
                    }
                    payInterfaceConfigList.add(payInterfaceConfig);
                });

        return payInterfaceConfigManager.saveMchChannel(channelId, payInterfaceConfigList, systemMchChannelConfigFlowList, mchChannelPermission.getClientId(), mchChannelPermission.getClientType());
    }

    /**
     * 获取商户已授权的支付渠道
     *
     * @param type  商户类型
     * @param mchId 商户ID
     */
    @Override
    public List<String> getMchChannel(Integer type, Long mchId) {
        List<PayInterfaceConfig> configList = payInterfaceConfigManager.lambdaQuery()
                .eq(PayInterfaceConfig::getClientId, mchId)
                .eq(PayInterfaceConfig::getClientType, type).list();
        return configList.stream().map(e -> String.valueOf(e.getInterfaceId())).toList();
    }

    /**
     * 获取当前商户渠道配置列表
     *
     * @param clientId 商户id
     */
    @Override
    public List<ClientChannelRespDTO> mchChannelList(Long clientId) {
        List<PayInterfaceConfig> configList = payInterfaceConfigManager.lambdaQuery().eq(PayInterfaceConfig::getClientId, clientId).list();

        if (CollectionUtils.isEmpty(configList)) {
            return Collections.emptyList();
        }

        List<Long> channelIdList = configList.stream().map(PayInterfaceConfig::getInterfaceId).toList();
        List<PayInterfaceDefine> interfaceDefineList = payInterFaceDefineManager.lambdaQuery().in(PayInterfaceDefine::getId, channelIdList).list();
        Map<Long, String> interfaceDefineNameByMap = interfaceDefineList.stream().collect(Collectors.toMap(PayInterfaceDefine::getId, PayInterfaceDefine::getName));
        return configList.stream().map(e -> {
            ClientChannelRespDTO clientChannelRespDTO = PayInterfaceConfigConvert.INSTANCE.toClientChannelRespDTO(e);
            clientChannelRespDTO.setChannelName(interfaceDefineNameByMap.get(e.getInterfaceId()));
            return clientChannelRespDTO;
        }).toList();
    }

    /**
     * 保存商户渠道配置信息
     *
     * @param channelConfigReq 渠道配置信息请求参数
     */
    @Override
    public String saveClientChannelConfig(ClientChannelConfigReqDTO channelConfigReq) {
        PayInterfaceDefine payInterfaceDefine = payInterFaceDefineManager.infoById(channelConfigReq.getChannelId());
        if (channelConfigReq.getClientType().equals(PayClientType.SERVICE_PROVIDER.code())) {
            List<DynamicFormUtil.DynamicForm> parse = JsonUtil.parseArray(payInterfaceDefine.getIsvParams(), DynamicFormUtil.DynamicForm.class);
            DynamicFormUtil.validateDynamicForm(parse, channelConfigReq.getDynamicForm());
        } else if (channelConfigReq.getClientType().equals(PayClientType.SUB_MERCHANT.code())) {
            List<DynamicFormUtil.DynamicForm> parse = JsonUtil.parseArray(payInterfaceDefine.getIsvSubMchParams(), DynamicFormUtil.DynamicForm.class);
            DynamicFormUtil.validateDynamicForm(parse, channelConfigReq.getDynamicForm());
        } else {
            List<DynamicFormUtil.DynamicForm> parse = JsonUtil.parseArray(payInterfaceDefine.getNormalMchParams(), DynamicFormUtil.DynamicForm.class);
            DynamicFormUtil.validateDynamicForm(parse, channelConfigReq.getDynamicForm());
        }
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigManager.lambdaQuery()
                .eq(PayInterfaceConfig::getClientId, channelConfigReq.getClientId())
                .eq(PayInterfaceConfig::getInterfaceId, channelConfigReq.getChannelId())
                .eq(PayInterfaceConfig::getClientType, channelConfigReq.getClientType()).one();
        Assert.isNull(payInterfaceConfig, ApiException.supplier(MERCHANT_HAS_NO_CHANNEL_PERMISSION));
        if (channelConfigReq.getClientType().equals(PayClientType.SERVICE_PROVIDER.code())) {
            payInterfaceConfig.setInterfaceRate(channelConfigReq.getIsvRate());
        }
        payInterfaceConfig.setEnable(channelConfigReq.getEnable());
        payInterfaceConfig.setInterfaceParams(JsonUtil.toJson(channelConfigReq.getDynamicForm()));
        boolean update = payInterfaceConfigManager.updateById(payInterfaceConfig);

        if (update) {
            return payInterfaceDefine.getCode();
        }

        return "";
    }

    /**
     * 获取客户端支付渠道配置信息
     *
     * @param channelId  支付渠道id
     * @param clientId   客户端id
     * @param clientType 客户端类型
     */
    @Override
    public ClientChannelConfigRespDTO clientChannelConfigInfo(Long channelId, Long clientId, Integer clientType) {
        PayInterfaceConfig interfaceConfig = payInterfaceConfigManager.lambdaQuery()
                .eq(PayInterfaceConfig::getClientId, clientId)
                .eq(PayInterfaceConfig::getInterfaceId, channelId)
                .eq(PayInterfaceConfig::getClientType, clientType).one();
        Assert.isNull(interfaceConfig, ApiException.supplier(MERCHANT_HAS_NO_CHANNEL_PERMISSION));
        ClientChannelConfigRespDTO result = new ClientChannelConfigRespDTO();
        result.setEnable(interfaceConfig.getEnable());
        result.setIsvRate(interfaceConfig.getInterfaceRate());
        if (StringUtils.hasText(interfaceConfig.getInterfaceParams())) {
            Map<String, Object> param = JsonUtil.toMap(interfaceConfig.getInterfaceParams());
            result.setDynamicForm(param);
        }
        List<SystemChannelFlowDefine> list = systemChannelFlowDefineManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getChannelCode, interfaceConfig.getInterfaceCode())
                .eq(SystemChannelFlowDefine::getClientType, clientType)
                .list();
        // 处理商户渠道配置流程
        SystemMchChannelConfigFlow mchChannelConfigFlow = systemMchChannelConfigFlowManager.lambdaQuery()
                .eq(SystemMchChannelConfigFlow::getClientId, clientId)
                .eq(SystemMchChannelConfigFlow::getClientType, clientType)
                .one();
        result.setFlow("");
        result.setChannelFlowList(new ArrayList<>());
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, SystemMchChannelConfigFlow.WholeFlow> map = new HashMap<>();
            if (!ObjectUtils.isEmpty(mchChannelConfigFlow) && StringUtils.hasText(mchChannelConfigFlow.getWholeFlow())) {
                List<SystemMchChannelConfigFlow.WholeFlow> wholeFlows = JsonUtil.parseArray(mchChannelConfigFlow.getWholeFlow(), SystemMchChannelConfigFlow.WholeFlow.class);
                map = wholeFlows.stream().collect(Collectors.toMap(SystemMchChannelConfigFlow.WholeFlow::getStepType, e -> e));
            }
            Map<String, SystemMchChannelConfigFlow.WholeFlow> finalMap = map;
            List<MchChannelFlowRespDTO> flowRespDTOList = list.stream().map(e -> {
                MchChannelFlowRespDTO mchChannelFlowResp = SystemChannelFlowDefineConvert.INSTANCE.toMchChannelFlowRespDTO(e);
                if (finalMap.containsKey(mchChannelFlowResp.getStepType())) {
                    SystemMchChannelConfigFlow.WholeFlow wholeFlow = finalMap.get(mchChannelFlowResp.getStepType());
                    mchChannelFlowResp.setHandleResult(StringUtils.hasText(wholeFlow.getHandleResult()) ? wholeFlow.getHandleResult() : wholeFlow.getErrorMsg());
                    mchChannelFlowResp.setStatus(wholeFlow.getStatus());
                    mchChannelFlowResp.setHandleTime(wholeFlow.getHandleTime());
                }
                return mchChannelFlowResp;
            }).toList();

            result.setChannelFlowList(flowRespDTOList);
        }

        return result;
    }

    /**
     * 执行支付渠道处理
     *
     * @param clientId    客户端id
     * @param channelCode 渠道编号
     * @param body        参数
     * @return 是否成功
     */
    @Override
    public Boolean executeChannelProcess(Long clientId, String channelCode, Object body) {
        // 获取当前支付渠道执行流程定义
        List<SystemChannelFlowDefine> list = systemChannelFlowDefineManager.lambdaQuery()
                .eq(SystemChannelFlowDefine::getChannelCode, channelCode).list();
        if (CollectionUtils.isEmpty(list)) {
            return Boolean.TRUE;
        }
        SystemMchChannelConfigFlow mchChannelConfigFlow = systemMchChannelConfigFlowManager.lambdaQuery()
                .eq(SystemMchChannelConfigFlow::getChannelCode, channelCode)
                .eq(SystemMchChannelConfigFlow::getClientId, clientId).one();

        Assert.isNull(mchChannelConfigFlow, ApiException.supplier(MERCHANT_CHANNEL_FLOW_DATA_EXCEPTION));

        Assert.isTrue(mchChannelConfigFlow.getStatus().equals(SystemMchChannelConfigFlow.Status.PROCESSING.code()), ApiException.supplier(MERCHANT_CHANNEL_FLOW_PROCESSING));
        int flowDefineCount = list.size() - 1;
        SystemChannelFlowDefine systemChannelFlowDefine = list.get(flowDefineCount);
        if (systemChannelFlowDefine.getStepOrder().equals(mchChannelConfigFlow.getIndex()) && mchChannelConfigFlow.getStatus().equals(SystemMchChannelConfigFlow.Status.SUCCESS.code())) {
            return Boolean.TRUE;
        }

        Map<Integer, SystemChannelFlowDefine> flowDefineMap = list.stream().collect(Collectors.toMap(SystemChannelFlowDefine::getStepOrder, e -> e));
        SystemChannelFlowDefine channelFlowDefine = flowDefineMap.get(mchChannelConfigFlow.getIndex());
        List<SystemChannelFlowDefine> channelFlowDefineList = new ArrayList<>();
        if (mchChannelConfigFlow.getStatus().equals(SystemMchChannelConfigFlow.Status.SUCCESS.code())) {
            boolean hasNextFlow = false;
            for (int i = mchChannelConfigFlow.getIndex() + 1; i <= flowDefineCount; i++) {
                SystemChannelFlowDefine flowDefine = flowDefineMap.get(i);
                // 拉取自动执行及用户调用组件进行统一执行处理
                if (StringUtils.hasText(flowDefine.getUserInput())) {
                    hasNextFlow = true;
                }
                channelFlowDefineList.add(flowDefine);
                if (hasNextFlow) {
                    break;
                }
            }
        }

        // 获取当前可执行的渠道

        return false;
    }

    /**
     * 获取商户支付渠道配置信息
     *
     * @param mchId       商户id
     * @param channelCode 支付渠道编码
     * @return 支付渠道配置信息
     */
    @Override
    public Map<String, String> mchChannelConfig(Long mchId, String channelCode) {
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigManager.lambdaQuery()
                .eq(PayInterfaceConfig::getClientId, mchId)
                .eq(PayInterfaceConfig::getInterfaceCode, channelCode).one();
        if (ObjectUtils.isEmpty(payInterfaceConfig)) {
            return Map.of();
        }

        try {
            return JsonUtil.getInstance().readValue(payInterfaceConfig.getInterfaceParams(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new ServiceException("配置信息异常");
        }
    }
}
