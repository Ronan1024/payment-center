package com.baosight.payment.system.dao.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baosight.database.core.manager.impl.BaseManagerImpl;
import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.annotation.Manager;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.dao.TongLianMchConfigDAO;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.convert.PayInterfaceConfigConvert;
import com.baosight.payment.system.dao.entity.SystemClientChannelPermission;
import com.baosight.payment.system.dao.entity.SystemMchChannelConfigFlow;
import com.baosight.payment.system.dao.mapper.SystemClientChannelPermissionMapper;
import com.baosight.payment.system.dao.mapper.SystemMchChannelConfigFlowMapper;
import com.baosight.payment.system.mapper.PayInterfaceConfigMapper;
import com.baosight.payment.system.mapper.PayTongLianRelevanceMapper;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.utils.stream.StreamBuild;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/26
 */
@Slf4j
@Manager
@RequiredArgsConstructor
public class PayInterfaceConfigManager extends BaseManagerImpl<PayInterfaceConfigMapper, PayInterfaceConfig> {
    private final PayInterfaceConfigMapper payInterfaceConfigMapper;
    private final PayTongLianRelevanceMapper payTongLianRelevanceMapper;
    private final SystemMchChannelConfigFlowMapper systemMchChannelConfigFlowMapper;
    private final SystemClientChannelPermissionMapper systemMchChannelPermissionMapper;

    /**
     * 保存支付接口配置
     *
     * @param payInterfaceConfig 支付接口保存配置类
     * @param payWayList         支付方式列表
     */
    public int saveInterfaceConfig(PayInterfaceConfig payInterfaceConfig, List<PayWay> payWayList) {
        // 支付机构分组
        List<String> distinctList = StreamBuild.of(payWayList).map(PayWay::getPayingAgency).toDistinctList();
        distinctList.stream().filter(e -> e.equals(PayingAgency.ALL_IN.code())).forEach(e -> {
            PayTongLianRelevance payTongLianRelevance = payTongLianRelevanceMapper.selectOne(new LambdaQueryWrapper<PayTongLianRelevance>()
                    .eq(PayTongLianRelevance::getMchId, payInterfaceConfig.getClientId()));
            if (!ObjectUtils.isEmpty(payTongLianRelevance)) {
                payTongLianRelevance = new PayTongLianRelevance();
                payTongLianRelevance.setMchId(payInterfaceConfig.getClientId());
                payTongLianRelevanceMapper.insert(payTongLianRelevance);
            }
        });
        return payInterfaceConfigMapper.insert(payInterfaceConfig);
    }

    /**
     * 获取通联关联信息
     *
     * @param mchId 商户id
     */
    public PayTongLianRelevance tongLianRelevance(Long mchId) {
        return payTongLianRelevanceMapper.selectOne(new LambdaQueryWrapper<PayTongLianRelevance>()
                .eq(PayTongLianRelevance::getMchId, mchId)
        );
    }

    /**
     * 获取通联支付配置
     *
     * @param mchId         商户id
     * @param interfaceCode 接口编号
     * @param isvId         服务商id
     */
    public TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig(Long mchId, String interfaceCode, Long isvId) {
        TongLianIsvConfigDAO tongLianIsvConfigDAO = tongLianIsvConfig(isvId, interfaceCode, null);
        TongLianMchConfigDAO tongLianMchConfigDAO = tongLianMchConfig(mchId, interfaceCode, null);
        return new TongLianIsvAndMchConfigDAO(tongLianIsvConfigDAO, tongLianMchConfigDAO);
    }

    /**
     * 获取通联服务商配置
     *
     * @param isvId         服务商id
     * @param interfaceCode 接口编号
     * @param interfaceId   接口id
     */
    public TongLianIsvConfigDAO tongLianIsvConfig(Long isvId, String interfaceCode, Long interfaceId) {
        LambdaQueryWrapper<PayInterfaceConfig> queryWrapper = getConfigQueryWrapper(interfaceCode, interfaceId, isvId, PayClientType.SERVICE_PROVIDER.code());
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(queryWrapper);
        //TODO 根据接口参数动态生成配置
//        List<DynamicForm> dynamicFormList = JsonUtil.parseArray(payInterfaceConfig.getInterfaceParams(), DynamicForm.class);
//        Map<String, Object> isv = dynamicFormList.stream().collect(Collectors.toMap(DynamicForm::getName, DynamicForm::getValue));
//        return JsonUtil.parse(JsonUtil.toJson(isv), TongLianIsvConfigDAO.class);
        return null;
    }

    /**
     * 获得通联商家配置
     *
     * @param mchId         商户id
     * @param interfaceCode 接口编号
     * @param interfaceId   接口id
     */
    public TongLianMchConfigDAO tongLianMchConfig(Long mchId, String interfaceCode, Long interfaceId) {
        if (ObjectUtils.isEmpty(mchId)) {
            return null;
        }
        LambdaQueryWrapper<PayInterfaceConfig> queryWrapper = getConfigQueryWrapper(interfaceCode, interfaceId, mchId, PayClientType.SUB_MERCHANT.code());
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(queryWrapper);
        // TODO 根据接口参数动态生成配置
//        List<DynamicForm> dynamicFormList = JsonUtil.parseArray(payInterfaceConfig.getInterfaceParams(), DynamicForm.class);
//        Map<String, Object> mch = dynamicFormList.stream().collect(Collectors.toMap(DynamicForm::getName, DynamicForm::getValue));
//        return JsonUtil.parse(JsonUtil.toJson(mch), TongLianMchConfigDAO.class);
        return null;
    }

    /**
     * 获取商户指定支付接口配置信息
     *
     * @param interfaceCode 接口code
     */
    public List<MchInterfaceConfigVO> mchConfig(String interfaceCode) {
        List<PayInterfaceConfig> payInterfaceConfigs = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getInterfaceCode, interfaceCode)
                .eq(PayInterfaceConfig::getClientType, PayClientType.SERVICE_PROVIDER.code())
        );

        if (CollectionUtils.isEmpty(payInterfaceConfigs)) {
            return new ArrayList<>();
        }

        return payInterfaceConfigs.stream().map(e -> {
            MchInterfaceConfigVO mchInterfaceConfigVO = PayInterfaceConfigConvert.INSTANCE.toMchInterfaceConfigVO(e);
            Map<String, String> apply = parseInterfaceParam.apply(e.getInterfaceParams());
            mchInterfaceConfigVO.setConfig(apply);
            return mchInterfaceConfigVO;
        }).toList();

    }

    /**
     * 获取下级子商户的配置信息
     *
     * @param isvId         服务商id
     * @param interfaceCode 支付接口编号
     */
    public List<MchInterfaceConfigVO> mchConfig(Long isvId, String interfaceCode) {
        List<PayInterfaceConfig> interfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getInterfaceCode, interfaceCode)
                .eq(PayInterfaceConfig::getParentClientId, isvId));
        return interfaceConfigList.stream().map(e -> {
            MchInterfaceConfigVO mchInterfaceConfigVO = PayInterfaceConfigConvert.INSTANCE.toMchInterfaceConfigVO(e);
            Map<String, String> apply = parseInterfaceParam.apply(e.getInterfaceParams());
            mchInterfaceConfigVO.setConfig(apply);
            return mchInterfaceConfigVO;
        }).toList();
    }


    /**
     * 获取支付接口配置查询query
     *
     * @param interfaceCode 接口编号
     * @param interfaceId   接口id
     * @param clientId      客户端id
     * @param clientType    客户端类型
     */
    private LambdaQueryWrapper<PayInterfaceConfig> getConfigQueryWrapper(String interfaceCode, Long interfaceId, Long clientId, Integer clientType) {
        Assert.isTrue(!StringUtils.hasText(interfaceCode) && ObjectUtils.isEmpty(interfaceId), "接口编码或接口id 不能同时为空");
        return new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(!ObjectUtils.isEmpty(interfaceId), PayInterfaceConfig::getInterfaceId, interfaceId)
                .eq(StringUtils.hasText(interfaceCode), PayInterfaceConfig::getInterfaceCode, interfaceCode)
                .eq(PayInterfaceConfig::getClientId, clientId)
                .eq(PayInterfaceConfig::getClientType, clientType);
    }


    public final Function<String, Map<String, String>> parseInterfaceParam = e -> {
        // TODO 根据接口参数动态生成配置
//        List<DynamicForm> dynamicFormList = JsonUtil.parseArray(e, DynamicForm.class);
//        return dynamicFormList.stream().collect(Collectors.toMap(DynamicForm::getName, va -> String.valueOf(va.getValue())));
        return new HashMap<>();
    };


    /**
     * 保存商户支付渠道配置
     *
     * @param channelDefineId   需要进行移除的渠道id
     * @param channelList 需要进行保存的渠道信息列表
     * @param clientId    操作的商户id
     * @param clientType  操作的商户类型
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveMchChannel(List<Long> channelDefineId, List<PayInterfaceConfig> channelList, List<SystemMchChannelConfigFlow> channelConfigFlowList, Long clientId, Integer clientType) {
        if (!CollectionUtils.isEmpty(channelDefineId)) {
            remove(this.lambdaQuery().in(PayInterfaceConfig::getInterfaceId, channelDefineId)
                    .eq(PayInterfaceConfig::getClientId, clientId)
                    .or().eq(PayInterfaceConfig::getParentClientId, clientId));
        }
        // 初始化商户渠道流程操作集
        if (!CollectionUtils.isEmpty(channelConfigFlowList)) {
            systemMchChannelConfigFlowMapper.insert(channelConfigFlowList);
        }

        // 保存新的商户支付渠道
        payInterfaceConfigMapper.insert(channelList);
        if (clientType.equals(PayClientType.SERVICE_PROVIDER.code())) {
            systemMchChannelPermissionMapper.delete(new LambdaQueryWrapper<SystemClientChannelPermission>()
                    .eq(SystemClientChannelPermission::getClientId, clientId)
                    .eq(SystemClientChannelPermission::getClientType, clientType));

            List<SystemClientChannelPermission> channelPermissionList = channelList.stream().map(e -> {
                SystemClientChannelPermission systemMchChannelPermission = new SystemClientChannelPermission();
                systemMchChannelPermission.setChannelCode(e.getInterfaceCode());
                systemMchChannelPermission.setClientId(clientId);
                systemMchChannelPermission.setClientType(clientType);
                systemMchChannelPermission.setChannelDefineId(e.getInterfaceId());
                return systemMchChannelPermission;
            }).toList();
            systemMchChannelPermissionMapper.insert(channelPermissionList);
        }
        return Boolean.TRUE;
    }

}
