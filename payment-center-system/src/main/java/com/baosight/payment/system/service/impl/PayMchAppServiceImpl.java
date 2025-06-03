package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.enums.State;
import com.baosight.payment.error.MchError;
import com.baosight.payment.system.convert.PayMchAppConvert;
import com.baosight.payment.system.mapper.*;
import com.baosight.payment.system.pojo.dto.CreateAppDTO;
import com.baosight.payment.system.pojo.dto.MchAppListDTO;
import com.baosight.payment.system.pojo.entity.*;
import com.baosight.payment.system.pojo.vo.MchAppListVO;
import com.baosight.payment.system.pojo.vo.MchPayAppInfoVO;
import com.baosight.payment.system.service.PayMchAppService;
import com.baosight.payment.system.service.PayMchPassageService;
import com.baosight.payment.vo.MchAppInfoVO;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.saas.context.AbstractUserContext;
import com.baosight.saas.context.SystemUserContext;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import com.baosight.web.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_app(商户应用表)】的数据库操作Service实现
 * @createDate 2025-02-24 16:58:18
 */
@Service
@RequiredArgsConstructor
public class PayMchAppServiceImpl extends ServiceImpl<PayMchAppMapper, PayMchApp> implements PayMchAppService {

    private final PayMchAppMapper payMchAppMapper;
    private final SaasAppPayRelevanceMapper saasAppPayRelevanceMapper;
    private final PayMchPassageService payMchPassageService;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Resource
    private MchInfoApi mchInfoApi;
    @Resource
    private PayInterfaceDefineMapper payInterfaceDefineMapper;
    @Resource
    private PayInterfaceConfigMapper payInterfaceConfigMapper;
    @Autowired
    private PayWayMapper payWayMapper;

    /**
     * 创建或者更新支付应用信息
     *
     * @param createAppDTO 创建应用id
     * @param mchId        商户id
     */
    @Override
    public Long createOrUpdate(CreateAppDTO createAppDTO, Long mchId) {
        MchInfoVO mchInfo = mchInfoApi.mchInfo(mchId);
        Assert.isNull(mchInfo, ApiException.supplier(MchError.MCH_NOT_FOUND));
        PayMchApp payMchApp = payMchAppMapper.selectOne(new LambdaQueryWrapper<PayMchApp>()
                .eq(PayMchApp::getMchId, mchId)
                .eq(PayMchApp::getAppName, createAppDTO.getName()));


        if (ObjectUtils.isEmpty(payMchApp)) {
            payMchApp = new PayMchApp();
            payMchApp.setMchId(mchId);
            payMchApp.setAppName(createAppDTO.getName());
            // 密钥
            byte[] randomBytes = new byte[32];
            SECURE_RANDOM.nextBytes(randomBytes);
            payMchApp.setAppSecret(Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes).replace("-", ""));
            payMchApp.setAppCode(SnowflakeIdUtil.nextIdStr());
            payMchApp.setCreateBy(AbstractUserContext.getUserId());
            payMchApp.setCreatedByName(AbstractUserContext.getUsername());
            payMchApp.setRemark(createAppDTO.getRemark());
            payMchAppMapper.insert(payMchApp);
            // 处理支付渠道
            handlerMchPayPassage(createAppDTO.getPayWay(), mchInfo, payMchApp.getId());
        } else {
            payMchApp.setAppName(createAppDTO.getName());
            payMchApp.setRemark(createAppDTO.getRemark());
            payMchAppMapper.updateById(payMchApp);
            // 删除原有的支付渠道信息
            payMchPassageService.remove(new LambdaQueryWrapper<PayMchPassage>()
                    .eq(PayMchPassage::getMchId, mchId)
                    .eq(PayMchPassage::getAppId, payMchApp.getId()));
            handlerMchPayPassage(createAppDTO.getPayWay(), mchInfo, payMchApp.getId());
        }
        return payMchApp.getId();
    }

    private void handlerMchPayPassage(List<Long> payWay, MchInfoVO mchInfo, Long appId) {
        List<PayWay> payWayList = payWayMapper.selectByIds(payWay);
        Map<Long, PayWay> payWayMap = payWayList.stream().collect(Collectors.toMap(PayWay::getId, e -> e));
        // TODO 支付渠道问题待处理
        // 获取已配置的信息
        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientId, mchInfo.getId()));

        List<PayInterfaceConfig> mchPayInterfaceConfigList = payInterfaceConfigList.stream().filter(e -> {
            List<Long> list = Arrays.stream(e.getPayWay().split(",")).map(Long::valueOf).toList();
            return new HashSet<>(payWay).containsAll(list);
        }).toList();
        Map<Long, Long> map = new HashMap<>();
        if (mchInfo.getType().equals(PayClientType.SUB_MERCHANT.getCode())) {
            List<Long> list = mchPayInterfaceConfigList.stream().map(PayInterfaceConfig::getInterfaceId).toList();
            List<PayInterfaceConfig> interfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                    .in(PayInterfaceConfig::getInterfaceId, list)
                    .eq(PayInterfaceConfig::getClientId, mchInfo.getIsvId())
            );
            Map<Long, Long> collect = interfaceConfigList.stream().collect(Collectors.toMap(PayInterfaceConfig::getInterfaceId, e -> ObjectUtils.isEmpty(e.getInterfaceRate()) ? 0 : e.getInterfaceRate()));
            map.putAll(collect);
        }

        payMchPassageService.remove(new LambdaQueryWrapper<PayMchPassage>()
                .eq(PayMchPassage::getMchId, mchInfo.getId())
                .eq(PayMchPassage::getAppId, appId)
        );
        // 保存支付渠道信息
        mchPayInterfaceConfigList.forEach(e -> {
            List<Long> list = Arrays.stream(e.getPayWay().split(",")).map(Long::valueOf).toList();
            list.stream().map(way -> {
                PayMchPassage payMchPassage = new PayMchPassage();
                payMchPassage.setAppId(appId);
                payMchPassage.setMchId(mchInfo.getId());
                payMchPassage.setCreateBy(AbstractUserContext.getUserId());
                PayWay payWayEntity = payWayMap.get(way);
                payMchPassage.setPayWayCode(payWayEntity.getPayCode());
                payMchPassage.setPayWayId(payWayEntity.getId());
                payMchPassage.setInterfaceId(e.getInterfaceId());
                payMchPassage.setCreateByName(AbstractUserContext.getUsername());
                payMchPassage.setState(State.NORMAL.getCode());
                payMchPassage.setInterfaceCode(e.getInterfaceCode());
                Long rate = map.getOrDefault(e.getInterfaceId(), null);
                payMchPassage.setRate(rate);
                return payMchPassage;
            }).forEach(payMchPassageService::save);
        });
    }

    /**
     * saas 获取商户应用配置详情 临时使用后期进行删除剥离
     *
     * @param appId saas应用id
     */
    @Override
    public MchPayAppInfoVO appInfoBySaasAppId(Integer appId) {
        Long mchId = SystemUserContext.getCompanyId();
        SaasAppPayRelevance saasAppPayRelevance = saasAppPayRelevanceMapper.selectOne(new LambdaQueryWrapper<SaasAppPayRelevance>()
                .eq(SaasAppPayRelevance::getAppId, appId)
                .eq(SaasAppPayRelevance::getMchId, mchId)
        );
        if (ObjectUtils.isEmpty(saasAppPayRelevance)) {
            return null;
        }
        return appInfo(saasAppPayRelevance.getId());
    }

    /**
     * 获取应用信息
     *
     * @param appId 应用id
     */
    @Override
    public MchPayAppInfoVO appInfo(Long appId) {
        PayMchApp payMchApp = payMchAppMapper.selectById(appId);
        return PayMchAppConvert.INSTANCE.toMchPayAppInfoVO(payMchApp);
    }

    /**
     * saas 创建商户应用
     *
     * @param create 创建应用请求体
     * @param appId  appId
     */
    @Override
    public Boolean saasCreateOrUpdate(CreateAppDTO create, Integer appId) {
        Long mchId = SystemUserContext.getCompanyId();
        SaasAppPayRelevance saasAppPayRelevance = saasAppPayRelevanceMapper.selectOne(new LambdaQueryWrapper<SaasAppPayRelevance>()
                .eq(SaasAppPayRelevance::getAppId, appId)
                .eq(SaasAppPayRelevance::getMchId, mchId));
        if (ObjectUtils.isEmpty(saasAppPayRelevance)) {
            Long id = createOrUpdate(create, mchId);
            SaasAppPayRelevance relevance = new SaasAppPayRelevance();
            relevance.setId(id);
            relevance.setAppId(appId);
            relevance.setMchId(mchId);
            saasAppPayRelevanceMapper.insert(relevance);
        } else {
            createOrUpdate(create, mchId);
        }
        return Boolean.TRUE;
    }

    /**
     * 获取支付商户应用信息
     *
     * @param mchId 商户ID
     * @param appId 应用ID
     */
    @Override
    public PayMchApp appInfo(Long mchId, Long appId) {
        return payMchAppMapper.selectOne(new LambdaQueryWrapper<PayMchApp>()
                .eq(PayMchApp::getId, appId)
                .eq(PayMchApp::getMchId, mchId)
        );
    }

    /**
     * 获取商家应用数据
     *
     * @param mchId         商家id
     * @param mchAppListDTO 获取商家应用列表请求数据
     */
    @Override
    public List<MchAppListVO> machAppList(Long mchId, MchAppListDTO mchAppListDTO) {
        List<PayMchApp> appList = payMchAppMapper.selectList(new LambdaQueryWrapper<PayMchApp>()
                .eq(PayMchApp::getMchId, mchId)
                .eq(StringUtils.hasText(mchAppListDTO.getAppCode()), PayMchApp::getAppCode, mchAppListDTO.getAppCode())
                .eq(!ObjectUtils.isEmpty(mchAppListDTO.getState()), PayMchApp::getState, mchAppListDTO)
                .like(StringUtils.hasText(mchAppListDTO.getName()), PayMchApp::getAppName, mchAppListDTO.getName())
        );

        if (CollectionUtils.isEmpty(appList)) {
            return new ArrayList<>();
        }
        return appList.stream().map(PayMchAppConvert.INSTANCE::toMchAppListVO).toList();
    }

    /**
     * 获取商户应用详情
     *
     * @param id 商户应用id
     */
    @Override
    public MchPayAppInfoVO info(Long id) {
        PayMchApp payMchApp = payMchAppMapper.selectById(id);
        List<PayMchPassage> payMchPassages = payMchPassageService.getPayPassageByAppId(payMchApp.getId(), payMchApp.getMchId());
        List<String> payWay = payMchPassages.stream().map(PayMchPassage::getPayWayId).map(String::valueOf).toList();
        MchPayAppInfoVO mchPayAppInfoVO = PayMchAppConvert.INSTANCE.toMchPayAppInfoVO(payMchApp);
        mchPayAppInfoVO.setPayWay(payWay);
        return mchPayAppInfoVO;
    }

    /**
     * 获取商家应用信息
     *
     * @param mchNo 商户id
     * @param appNo 应用编号
     */
    @Override
    public MchAppInfoVO appInfo(Long mchNo, String appNo) {
        PayMchApp payMchApp = payMchAppMapper.selectOne(new LambdaQueryWrapper<PayMchApp>()
                .eq(PayMchApp::getMchId, mchNo)
                .eq(PayMchApp::getAppCode, appNo)
                .eq(PayMchApp::getState, State.NORMAL.getCode())
        );
        if (ObjectUtils.isEmpty(payMchApp)) {
            return null;
        }
        return PayMchAppConvert.INSTANCE.toMchAppInfoVO(payMchApp);
    }

}




