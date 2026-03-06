package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.dao.TongLianMchConfigDAO;
import com.baosight.payment.enums.MchType;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.error.MchError;
import com.baosight.payment.system.convert.PayInterfaceConfigConvert;
import com.baosight.payment.system.error.PayInterfaceConfigError;
import com.baosight.payment.system.error.PayInterfaceError;
import com.baosight.payment.system.manager.PayInterfaceConfigManager;
import com.baosight.payment.system.mapper.PayInterfaceConfigMapper;
import com.baosight.payment.system.mapper.PayInterfaceDefineMapper;
import com.baosight.payment.system.pojo.dto.PayInterfaceConfigDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceDefineBindDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigDynamicVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.payment.system.service.PayWayService;
import com.baosight.payment.vo.IsvInterfaceConfigVO;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.saas.entity.DynamicForm;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.stream.StreamBuild;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import com.baosight.web.core.exception.ApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ronan.common.enums.IBaseEnum;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.baosight.payment.system.error.PayInterfaceError.PAY_INTERFACE_UPDATE_CONFIG_FAIL;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_config(支付接口配置)】的数据库操作Service实现
 * @createDate 2025-01-20 15:28:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayInterfaceConfigServiceImpl extends ServiceImpl<PayInterfaceConfigMapper, PayInterfaceConfig> implements PayInterfaceConfigService {
    private final PayInterfaceConfigMapper payInterfaceConfigMapper;
    private final PayInterfaceDefineMapper payInterfaceDefineMapper;

    private final PayInterfaceDefineService payInterfaceDefineService;
    private final PayInterfaceConfigManager payInterfaceConfigManager;
    private final PayWayService payWayService;

    @Resource
    private MchInfoApi mchInfoApi;

    @Override
    public Boolean setPaymentConfig(PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO) {
        return null;
    }

    @Override
    public List<PayInterfaceConfigDynamicVO> getIsvPayInterfaceConfigs(Long isvId) {
        List<PayInterfaceConfig> payInterfaceConfigs = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientType, PayClientType.SERVICE_PROVIDER.code())
                .eq(PayInterfaceConfig::getClientId, isvId));
        Assert.isTrue(payInterfaceConfigs.isEmpty(), ApiException.supplier(PayInterfaceError.PAY_INTERFACE_ISV_NOT_ENABLE));

        // 根据接口定义中的参数名称，获取当前的参数值，组织到List<PayInterfaceConfigVO> 并返回
        return getPayInterfaceConfigs(payInterfaceConfigs);
    }

    /**
     * 获取商户的支付配置列表
     * @param mchId
     * @return
     */
    @Override
    public List<PayInterfaceConfigDynamicVO> getMchPayInterfaceConfigs(Long mchId) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        Assert.isNull(mchInfoVO, ApiException.supplier(MchError.MCH_NOT_FOUND));
//        // 如果是特约商户则只返回服务商已开通的支付接口
//        if (mchInfoVO.getType().equals(PayClientType.SUB_MERCHANT.code())) {
//            return getIsvPayInterfaceConfigs(mchInfoVO.getIsvId());
//        } else {

            List<PayInterfaceConfig> payInterfaceConfigs = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                    .eq(PayInterfaceConfig::getClientType,mchInfoVO.getType())
                    .eq(PayInterfaceConfig::getClientId, mchId));
            if(payInterfaceConfigs.isEmpty()){
                return new ArrayList<>();
            }
            return getPayInterfaceConfigs(payInterfaceConfigs);
//        }

    }

    @Override
    public Boolean addPayInterfaceConfig(PayInterfaceDefineBindDTO payInterfaceDefineBindDTO) {
        List<PayInterfaceConfig> payInterfaceConfigs = new ArrayList<>();
        Long mchId = payInterfaceDefineBindDTO.getMchId();
        Integer payClientType = payInterfaceDefineBindDTO.getPayClientType();
        List<Long> subMerchantIdList = null; // 服务商下的子账户ID
        if(payClientType.equals(PayClientType.SERVICE_PROVIDER.code())){
            subMerchantIdList = mchInfoApi.mchInfoByIsvId(mchId);
        }
        List<Long> payInterfaceIdList = payInterfaceDefineBindDTO.getPayInterfaceIdList();
        // 先删除之前绑定的接口
        payInterfaceConfigMapper.delete(new LambdaQueryWrapper<PayInterfaceConfig>().eq(PayInterfaceConfig::getClientId,mchId));

        // 获取支付接口的定义信息
        for (Long payInterfaceId : payInterfaceIdList) {
            PayInterfaceDefine payInterFaceDefine = payInterfaceDefineService.getById(payInterfaceId);

            // 组织配置数据
            PayInterfaceConfig payInterfaceConfig = new PayInterfaceConfig();
            payInterfaceConfig.setClientId(mchId);
            payInterfaceConfig.setPayingAgency(payInterFaceDefine.getPayingAgency());
            payInterfaceConfig.setName(payInterFaceDefine.getName());
            payInterfaceConfig.setPayWay(payInterFaceDefine.getPayWay());
            payInterfaceConfig.setEnable(Boolean.FALSE);
            payInterfaceConfig.setCreateBy(UserContext.INSTANCE.userId());
            // interface_rate 签约成功后更新
            // mchNo 商户号，后续进行支付配置时设置
            // payInterfaceConfig.setMchNo();
            // mch_channel_user 绑定收银宝后更新

            // 服务商绑定支付接口
            if(payClientType.equals(PayClientType.SERVICE_PROVIDER.code())){
                payInterfaceConfig.setClientType(PayClientType.SERVICE_PROVIDER.code());
                payInterfaceConfig.setInterfaceParams(payInterFaceDefine.getIsvParams());

                // 处理服务商下面的特约商户
                if (subMerchantIdList != null) {
                    for (Long clientId : subMerchantIdList) {
                        // 组织配置数据
                        PayInterfaceConfig payInterfaceConfigSubMerchant = new PayInterfaceConfig();
                        payInterfaceConfigSubMerchant.setClientId(clientId);
                        payInterfaceConfigSubMerchant.setPayingAgency(payInterFaceDefine.getPayingAgency());
                        payInterfaceConfigSubMerchant.setName(payInterFaceDefine.getName());
                        payInterfaceConfigSubMerchant.setPayWay(payInterFaceDefine.getPayWay());
                        payInterfaceConfigSubMerchant.setEnable(Boolean.FALSE);
                        payInterfaceConfigSubMerchant.setCreateBy(UserContext.INSTANCE.userId());
                        payInterfaceConfigSubMerchant.setClientType(PayClientType.SUB_MERCHANT.code());
                        payInterfaceConfigSubMerchant.setInterfaceParams(payInterFaceDefine.getIsvSubMchParams()); // 设置为接口的子商户参数
                        payInterfaceConfigSubMerchant.setParentClientId(mchId);

                        payInterfaceConfigs.add(payInterfaceConfigSubMerchant);
                    }
                }

            }else if (payClientType.equals(PayClientType.MERCHANT.code())){
                // 普通商户
                payInterfaceConfig.setClientType(PayClientType.MERCHANT.code());
                payInterfaceConfig.setInterfaceParams(payInterFaceDefine.getNormalMchParams());
            }
            payInterfaceConfigs.add(payInterfaceConfig);
        }

        payInterfaceConfigMapper.insert(payInterfaceConfigs);
        return Boolean.TRUE;
    }

    /**
     * 特约商户绑定服务商，特约商户默认使用服务商的接口配置
     * @param isvId
     * @return
     */
    @Override
    public Boolean bindIsv(Long mchId,Long isvId) {
        // 先清空原有记录
        payInterfaceConfigMapper.delete(new LambdaQueryWrapper<PayInterfaceConfig>().eq(PayInterfaceConfig::getClientId,mchId));
        List<PayInterfaceConfig> payInterfaceConfigs = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>().eq(PayInterfaceConfig::getClientId, isvId));
        Assert.isTrue(payInterfaceConfigs.isEmpty(),"服务商未绑定支付接口");
        for (PayInterfaceConfig payInterfaceConfig : payInterfaceConfigs) {
            payInterfaceConfig.setClientId(mchId);
            payInterfaceConfig.setClientType(PayClientType.SUB_MERCHANT.code());
            String name = payInterfaceConfig.getName();
            // 根据接口名称查找接口
            PayInterfaceDefine payInterfaceDefine = payInterfaceDefineService.getOne(new LambdaQueryWrapper<PayInterfaceDefine>().eq(PayInterfaceDefine::getName, name));
            payInterfaceConfig.setInterfaceParams(payInterfaceDefine.getIsvSubMchParams()); // 设置为接口的子商户参数

            payInterfaceConfig.setId(null);
            payInterfaceConfig.setRemark(null);
            payInterfaceConfig.setEnable(Boolean.FALSE);
        }
        payInterfaceConfigMapper.insert(payInterfaceConfigs);
        return Boolean.TRUE;
    }


    /**
     * 根据接口定义和接口配置动态返回支付配置信息
     * @param payInterfaceConfigs
     * @return
     */
    public List<PayInterfaceConfigDynamicVO> getPayInterfaceConfigs(List<PayInterfaceConfig> payInterfaceConfigs) {
        List<PayInterfaceConfigDynamicVO> payInterfaceConfigDynamicVOS = new ArrayList<>();
        for (PayInterfaceConfig payInterfaceConfig : payInterfaceConfigs) {
            PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO = new PayInterfaceConfigDynamicVO();

            String params = payInterfaceConfig.getInterfaceParams();

            List<DynamicForm> paramList = JsonUtil.parse(params, new TypeReference<List<DynamicForm>>() {
            });
            payInterfaceConfigDynamicVO.setId(payInterfaceConfig.getId());
            payInterfaceConfigDynamicVO.setClientId(payInterfaceConfig.getClientId());
            payInterfaceConfigDynamicVO.setName(payInterfaceConfig.getName());
            payInterfaceConfigDynamicVO.setEnable(payInterfaceConfig.getEnable());
            payInterfaceConfigDynamicVO.setRemark(payInterfaceConfig.getRemark());
            payInterfaceConfigDynamicVO.setInterfaceRate(payInterfaceConfig.getInterfaceRate());
            payInterfaceConfigDynamicVO.setParamList(paramList);
            payInterfaceConfigDynamicVO.setCreateTime(payInterfaceConfig.getCreateTime());
            payInterfaceConfigDynamicVO.setUpdateTime(payInterfaceConfig.getUpdateTime());
            payInterfaceConfigDynamicVOS.add(payInterfaceConfigDynamicVO);

        }
        return payInterfaceConfigDynamicVOS;
    }

    @Override
    public Boolean updatePayInterfaceConfig4Mch(PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO) {
        // 判断商户类型
        Long mchId = payInterfaceConfigDynamicVO.getClientId();
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectById(payInterfaceConfigDynamicVO.getId());
        if(mchInfoVO.getType().equals(PayClientType.SUB_MERCHANT.code()) && !payInterfaceConfig.getInterfaceRate().equals(payInterfaceConfigDynamicVO.getInterfaceRate())){
            throw new IllegalStateException("不支持特约商户修改支付费率");
        }

        List<DynamicForm> paramList = payInterfaceConfigDynamicVO.getParamList();
        payInterfaceConfig.setInterfaceParams(JsonUtil.toJson(paramList));
        payInterfaceConfig.setEnable(payInterfaceConfigDynamicVO.getEnable());
        payInterfaceConfig.setInterfaceRate(payInterfaceConfigDynamicVO.getInterfaceRate());
        payInterfaceConfig.setRemark(payInterfaceConfigDynamicVO.getRemark());
        // 更新数据至数据库
        payInterfaceConfigMapper.updateById(payInterfaceConfig);

        return Boolean.TRUE;
    }
    @Override
    public Boolean updatePayInterfaceConfig4Isv(PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO) {
        // 查询该服务商的子商户
        Long isvId = payInterfaceConfigDynamicVO.getClientId();
        List<Long> mchIds = mchInfoApi.mchInfoByIsvId(isvId);

        // 先更新服务商的配置
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectById(payInterfaceConfigDynamicVO.getId());

        List<DynamicForm> paramList = payInterfaceConfigDynamicVO.getParamList();
        payInterfaceConfig.setInterfaceParams(JsonUtil.toJson(paramList));
        payInterfaceConfig.setEnable(payInterfaceConfigDynamicVO.getEnable());
        payInterfaceConfig.setInterfaceRate(payInterfaceConfigDynamicVO.getInterfaceRate());
        payInterfaceConfig.setRemark(payInterfaceConfigDynamicVO.getRemark());
        // 更新数据至数据库
        payInterfaceConfigMapper.updateById(payInterfaceConfig);

        List<PayInterfaceConfig> payInterfaceConfigs = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>().eq(PayInterfaceConfig::getName, payInterfaceConfig.getName())
                .in(PayInterfaceConfig::getClientId, mchIds));

        for (PayInterfaceConfig interfaceConfig : payInterfaceConfigs) {
            interfaceConfig.setInterfaceRate(payInterfaceConfigDynamicVO.getInterfaceRate());
        }

        payInterfaceConfigMapper.insertOrUpdate(payInterfaceConfigs);
        return Boolean.TRUE;
    }

//    private final PayInterfaceDefineService payInterfaceDefineService;
//    private final PayInterfaceConfigManager payInterfaceConfigManager;
//    private final PayWayService payWayService;
//    private final PayAgencyOptionContext payAgencyOptionContext;
//
//    @Resource
//    private MchInfoApi mchInfoApi;
//
    /**
     * 获取指定用户的支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param clientInfoId  支付客户信息id
     * @return 客户支付配置
     */
    @Override
    public List<PayInterfaceConfig> getPayConfiguration(PayClientType payClientType, Long clientInfoId) {
        return payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientType, payClientType.code())
                .eq(PayInterfaceConfig::getClientId, clientInfoId)
        );
    }
//
//    /**
//     * 获取指定用户的支付配置信息
//     *
//     * @param mchId 支付客户端类型
//     * @return 客户支付配置
//     */
//    @Override
//    public List<PayInterfaceConfig> getPayConfiguration(Long mchId) {
//        return payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
//                .eq(PayInterfaceConfig::getClientId, mchId)
//        );
//    }
//
    /**
     * 获取指定用户的支付配置信息
     *
     * @param mchId 商户id
     * @return 客户支付配置
     */
    @Override
    public Map<Long, PayInterfaceConfig> getPayConfigurationMap(Long mchId) {
        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientId, mchId));
        return StreamBuild.of(payInterfaceConfigList).toMap(PayInterfaceConfig::getInterfaceId, e -> e);
    }

    /**
     * 获取服务商支付配置信息
     *
     * @param payClientType 支付客户端类型
     * @param id            服务商id
     * @param interfaceId   支付接口ID
     * @return 支付接口配置信息
     */
     @Override
    public PayInterfaceConfigVO getConfigInfo(PayClientType payClientType, Long id, Long interfaceId) {
        PayInterfaceConfig payInterfaceConfig = byPayInterfaceconfig(payClientType, id, interfaceId);
        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineService.payInterfaceDefineBy(interfaceId);
        Long interfaceRate = 0L;
        if (payClientType.equals(PayClientType.SUB_MERCHANT)) {
            MchInfoVO mchInfoVO = mchInfoApi.mchInfo(id);
            // 如果是子商户，需要获取服务商的费率
            PayInterfaceConfig config = byPayInterfaceconfig(PayClientType.SERVICE_PROVIDER, mchInfoVO.getIsvId(), interfaceId);
            interfaceRate = config.getInterfaceRate();
        }
        if (!ObjectUtils.isEmpty(payInterfaceConfig)) {
            PayInterfaceConfigVO result = PayInterfaceConfigConvert.INSTANCE.toPayInterfaceConfigVO(payInterfaceConfig);
            //TODO 待处理
//            List<DynamicForm> dynamicFormList = JsonUtil.parseArray(result.getInterfaceParams(), DynamicForm.class);
//            List<DynamicForm> dynamicForms = JsonUtil.parseArray(payInterfaceDefine.interfaceParam(payClientType), DynamicForm.class);
            // TODO 当前只解决了新增新的配置 未处理变更的
//            if (dynamicFormList.size() != dynamicForms.size()) {
//                Map<String, DynamicForm> map = dynamicFormList.stream().collect(Collectors.toMap(DynamicForm::getName, e -> e));
//                dynamicFormList.addAll(dynamicForms.stream().filter(e -> !map.containsKey(e.getName())).toList());
//            }
            result.setInterfaceRate(payClientType.equals(PayClientType.SUB_MERCHANT) ? interfaceRate : payInterfaceConfig.getInterfaceRate());
//            result.setInterfaceParam(dynamicFormList);
            result.setHasSetting(Boolean.TRUE);
            return result;
        } else {
            // 获取接口配置信息
            PayInterfaceConfigVO result = new PayInterfaceConfigVO();
            result.setInterfaceId(payInterfaceDefine.getId());
            result.setEnable(Boolean.FALSE);
            result.setClientType(payClientType.code());
            result.setClientId(id);
            String interfaceParams = payInterfaceDefine.interfaceParam(payClientType);
            if (StringUtils.hasText(interfaceParams)) {
                result.setInterfaceParams(interfaceParams);
                // TODO 待处理
//                result.setInterfaceParam(JsonUtil.parseArray(interfaceParams, DynamicForm.class));
            }
            result.setHasSetting(Boolean.FALSE);
            result.setInterfaceRate(interfaceRate);
            result.setName(payInterfaceDefine.getName());
            return result;
        }
    }

    /**
     * 保存或更新支付配置
     *
     * @param payInterfaceConfigDTO 保存或更新
     * @param payClientType         支付客户端信息
     */
    @Override
    public Boolean payConfigurationSaveOrUpdate(PayInterfaceConfigDTO payInterfaceConfigDTO, PayClientType payClientType, Long mchId, Boolean hasMch) {
        // 存入真实费率
        if (payClientType.equals(PayClientType.SERVICE_PROVIDER) && !ObjectUtils.isEmpty(payInterfaceConfigDTO.getInterfaceRate())) {
            double value = new BigDecimal(payInterfaceConfigDTO.getInterfaceRate()).divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP).doubleValue();
            Assert.isTrue(value < 0 || value > 100, ApiException.supplier(PayInterfaceError.PAY_INTERFACE_RATE_ERROR, String.valueOf(value)));
        }
        String mchNo = "";
        if (Boolean.TRUE.equals(hasMch)) {
            MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
            Assert.isNull(mchInfoVO, ApiException.supplier(MchError.MCH_NOT_FOUND));
            mchNo = mchInfoVO.getMchNo();
        }
        PayInterfaceConfig payInterfaceConfig = byPayInterfaceconfig(payClientType, mchId, payInterfaceConfigDTO.getInterfaceId());
        // 获取接口定义信息
        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineService.payInterfaceDefineBy(payInterfaceConfigDTO.getInterfaceId());
        Assert.isFalse(!ObjectUtils.isEmpty(payInterfaceDefine) && payInterfaceDefine.getEnable(), () -> new ApiException(PayInterfaceError.PAY_INTERFACE_DISABLED));
        Assert.isNull(payInterfaceDefine.getIsvParams(), () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NOT_ISV));
        var param = new Object() {
//            List<DynamicForm> dynamicFormList;
        };
        Map<String, String> map = payInterfaceConfigDTO.getInterfaceParam().stream().collect(Collectors.toMap(PayInterfaceConfigDTO.InterfaceParam::getName, PayInterfaceConfigDTO.InterfaceParam::getValue));
        String interfaceParam = payInterfaceDefine.interfaceParam(payClientType);
        if (StringUtils.hasText(interfaceParam)) {
//            List<DynamicForm> dynamicFormList = JsonUtil.parseArray(interfaceParam, DynamicForm.class);
//            dynamicFormList.forEach(e -> {
//                e.setValue(map.get(e.getName()));
//                Assert.isTrue(e.getRequired() && ObjectUtils.isEmpty(e.getValue()), ApiException.supplier(PaymentError.PARAMS_NOT_NULL, e.getErrorMessage()));
//            });
//            param.dynamicFormList = dynamicFormList;
        }

//        Long userId = AbstractUserContext.getUserId();
        int option;
        // 处理支付配置参数信息
        if (ObjectUtils.isEmpty(payInterfaceConfig)) {
            // 不存在保存
            PayInterfaceConfig save = PayInterfaceConfigConvert.INSTANCE.toEntity(payInterfaceConfigDTO);
            save.setName(payInterfaceDefine.getName());
//            save.setInterfaceParams(JsonUtil.toJson(param.dynamicFormList));
            save.setClientType(payClientType.code());
            save.setInterfaceRate(payInterfaceConfigDTO.getInterfaceRate());
//            save.setCreateBy(userId);
            save.setClientId(mchId);
            save.setMchNo(mchNo);
            save.setPayWay(payInterfaceDefine.getPayWay());
            save.setPayingAgency(payInterfaceDefine.getPayingAgency());
            save.setInterfaceCode(payInterfaceDefine.getCode());
            save.setMchChannelUser(map.get(payInterfaceDefine.getMchChannelUserKey()));
            // 处理扩展资源信息
            List<Long> payWayidList = Arrays.stream(payInterfaceDefine.getPayWay().split(",")).map(Long::valueOf).toList();
            List<PayWay> payWayList = payWayService.selectByIdList(payWayidList);
            option = payInterfaceConfigManager.saveInterfaceConfig(save, payWayList);
        } else {
            // 更新
//            payInterfaceConfig.setInterfaceParams(JsonUtil.toJson(param.dynamicFormList));
//            payInterfaceConfig.setUpdateBy(userId);
            PayInterfaceConfigConvert.INSTANCE.copyPayInterfaceConfig(payInterfaceConfig, payInterfaceConfigDTO);
            payInterfaceConfig.setName(payInterfaceDefine.getName());
            payInterfaceConfig.setPayingAgency(payInterfaceDefine.getPayingAgency());
            // TODO 接口定义修改了支付方式， 支付配置中不会进行同步更新， 后去流程需要进行优化， 是采用用户选择的方式， 还是采用接口直接更新的方式
            option = payInterfaceConfigMapper.updateById(payInterfaceConfig);

        }
        Assert.isFalse(option > 0, () -> new ApiException(PAY_INTERFACE_UPDATE_CONFIG_FAIL));
        // TODO (L.J.Ran 2025/3/24 - P2-1742871593 describe: 推送mq到目前节点进行更新数据 )
//        mqSender.send(ResetIsvMchAppInfoConfigMQ.build(ResetIsvMchAppInfoConfigMQ.RESET_TYPE_ISV_INFO, infoId, null, null));

        return Boolean.TRUE;
    }

    /**
     * 获取指定的客户端下指定支付接口配置
     *
     * @param payClientType 支付客户端类型
     * @param clientId      客户端用户唯一id
     * @param interfaceId   支付接口id
     */
    @Override
    public PayInterfaceConfig byPayInterfaceconfig(PayClientType payClientType, Long clientId, Long interfaceId) {
        return payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientType, payClientType.code())
                .eq(PayInterfaceConfig::getClientId, clientId)
                .eq(PayInterfaceConfig::getInterfaceId, interfaceId)
        );
    }

    @Override
    public List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId) {
        return null;
    }
//
//// TODO 待处理
////    /**
////     * 获取用户指定接口配置信息
////     *
////     * @param mchId       商户id
////     * @param interfaceId 接口id
////     */
////    @Override
////    public List<DynamicForm> getDynamicForm(Long mchId, Long interfaceId) {
////        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
////                .eq(PayInterfaceConfig::getClientId, mchId)
////                .eq(PayInterfaceConfig::getInterfaceId, interfaceId));
////        if (!ObjectUtils.isEmpty(payInterfaceConfig)) {
////            return JsonUtil.parseArray(payInterfaceConfig.getInterfaceParams(), DynamicForm.class);
////        }
////        return Collections.emptyList();
////    }
//
//    /**
//     * 获取支付配置列表
//     *
//     * @param isvId 商户id
//     */
//    @Override
//    public List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId) {
//        List<PayInterfaceDefineListVO> payInterfaceDefineList = payInterfaceDefineService.getPayInterfaceDefineList(PayClientType.SERVICE_PROVIDER);
//        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
//                .eq(PayInterfaceConfig::getClientType, PayClientType.SERVICE_PROVIDER.code())
//                .eq(PayInterfaceConfig::getClientId, isvId)
//        );
//        return function.apply(payInterfaceDefineList, payInterfaceConfigList);
//    }
//
    /**
     * 获取指定商户签约的指定支付机构的接口ID
     *
     * @param mchId        商户id
     * @param payingAgency 支付机构信息
     */
    @Override
    public Long getMchInterfaceIdByPayingAgency(Long mchId, PayingAgency payingAgency) {
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientId, mchId)
                .eq(PayInterfaceConfig::getPayingAgency, payingAgency.code())
        );
        if (ObjectUtils.isEmpty(payInterfaceConfig)) {
            return null;
        }
        return payInterfaceConfig.getInterfaceId();
    }
    /**
     * 根据指定商户获取指定支付机构配置信息
     *
     * @param mchId        商户id
     * @param payingAgency 支付机构信息
     */
    @Override
    public PayInterfaceConfigVO getConfigInfo(Long mchId, PayingAgency payingAgency) {
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientId, mchId)
                .eq(PayInterfaceConfig::getPayingAgency, payingAgency.code())
        );
        if (ObjectUtils.isEmpty(payInterfaceConfig)) {
            return null;
        }
        PayInterfaceConfigVO result = new PayInterfaceConfigVO();
        result.setInterfaceId(payInterfaceConfig.getId());
        result.setEnable(Boolean.FALSE);
        result.setClientType(payInterfaceConfig.getClientType());
        result.setClientId(mchId);
        result.setInterfaceParams(payInterfaceConfig.getInterfaceParams());
        // TODO 待处理
//        result.setInterfaceParam(JsonUtil.parseArray(payInterfaceConfig.getInterfaceParams(), DynamicForm.class));
        result.setHasSetting(Boolean.FALSE);
        result.setName(payInterfaceConfig.getName());
        result.setPayingAgency(payInterfaceConfig.getPayingAgency());
        return result;
    }

    /**
     * 获取指定客户端配置信息
     *
     * @param clientId     客户端id
     * @param payingAgency 支付机构信息
     * @param interfaceId  支付接口id
     */
    @Override
    public PayInterfaceConfigVO getConfigInfo(Long clientId, PayingAgency payingAgency, Long interfaceId) {
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientId, clientId)
                .eq(PayInterfaceConfig::getPayingAgency, payingAgency.code())
                .eq(PayInterfaceConfig::getInterfaceId, interfaceId)
        );
        if (ObjectUtils.isEmpty(payInterfaceConfig)) {
            return null;
        }
        PayInterfaceConfigVO result = PayInterfaceConfigConvert.INSTANCE.toPayInterfaceConfigVO(payInterfaceConfig);
        result.setInterfaceParam(JsonUtil.parseArray(payInterfaceConfig.getInterfaceParams(), DynamicForm.class));
        // TODO 待处理
//        result.setInterfaceParam(JsonUtil.parseArray(payInterfaceConfig.getInterfaceParams(), DynamicForm.class));
        return result;
    }
//
//    /**
//     * 商户id 获取商户接口配置信息
//     *
//     * @param isvId 商户id
//     */
//    @Override
//    public PayInterfaceConfig getIsvInterfaceConfig(Long isvId) {
//        return payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
//                .eq(PayInterfaceConfig::getClientId, isvId)
//        );
//
//    }
//
//
//    /**
//     * 根据账号类型与信息id 获取支付接口配置列表
//     *
//     * @param accountType 账号类型
//     * @param infoId      详情id
//     */
//    @Override
//    public List<PayInterfaceConfig> payInterfaceConfigByTypeAndInfoId(Integer accountType, Long infoId) {
//        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
//                .eq(PayInterfaceConfig::getClientType, accountType)
//                .eq(PayInterfaceConfig::getClientId, infoId)
//        );
//        if (CollectionUtils.isEmpty(payInterfaceConfigList)) {
//            return new ArrayList<>();
//        }
//        return payInterfaceConfigList;
//    }

    /**
     * 获取商户支付配置列表
     *
     * @param mchId 商户id
     */
    @Override
    public PayInterfaceConfigDynamicVO getMchInterfaceConfigList(Long mchId) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        Assert.isNull(mchInfoVO, ApiException.supplier(MchError.MCH_NOT_FOUND));
        // 如果是特约商户则只返回服务商已开通的支付接口
        PayClientType payClientType = IBaseEnum.getByCode(PayClientType.class, mchInfoVO.getType());
        List<PayInterfaceDefineListVO> payInterfaceDefineList;
        if (payClientType.equals(PayClientType.SUB_MERCHANT)) {
            List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                    .eq(PayInterfaceConfig::getClientType, PayClientType.SERVICE_PROVIDER.code())
                    .eq(PayInterfaceConfig::getClientId, mchInfoVO.getIsvId())
                    .eq(PayInterfaceConfig::getEnable, Boolean.TRUE)
            );
            Assert.isNull(payInterfaceConfigList, ApiException.supplier(PayInterfaceError.PAY_INTERFACE_ISV_NOT_ENABLE));
            List<Long> interfaceIdList = StreamBuild.of(payInterfaceConfigList).toList(PayInterfaceConfig::getInterfaceId);
            payInterfaceDefineList = payInterfaceDefineService.getPayInterfaceDefineList(payClientType, interfaceIdList);
        } else {
            payInterfaceDefineList = payInterfaceDefineService.getPayInterfaceDefineList(payClientType);
        }
        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientType, mchInfoVO.getType())
                .eq(PayInterfaceConfig::getClientId, mchId)
        );

//        return function.apply(payInterfaceDefineList, payInterfaceConfigList);
        return null;
    }

    /**
     * 获取商户支付接口配置
     *
     * @param mchId 商户id
     * @param type  商户类型
     */
    @Override
    public List<PayInterfaceConfig> getMchInterfaceConfig(Long mchId, Integer type) {
        List<PayInterfaceConfig> configList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientId, mchId)
                .eq(PayInterfaceConfig::getClientType, type)
                .eq(PayInterfaceConfig::getEnable, Boolean.TRUE)
        );

        Assert.isNull(configList, ApiException.supplier(PayInterfaceError.PAY_INTERFACE_CHANNEL_NOT_CONFIG));
        return configList;
    }

    /**
     * 根据接口code 以及 渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    @Override
    public MchInterfaceConfigVO mchInterfaceConfig(String interfaceCode, String mchChannelUser) {
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getMchChannelUser, mchChannelUser)
                .eq(PayInterfaceConfig::getInterfaceCode, interfaceCode)
        );
        if (ObjectUtils.isEmpty(payInterfaceConfig)) {
            return null;
        }
        MchInterfaceConfigVO result = PayInterfaceConfigConvert.INSTANCE.toMchInterfaceConfigVO(payInterfaceConfig);
        String interfaceParam = payInterfaceConfig.getInterfaceParams();
        // TODO 待处理
//        List<DynamicForm> dynamicFormList = JsonUtil.parseArray(interfaceParam, DynamicForm.class);
//        Map<String, String> collect = dynamicFormList.stream().collect(Collectors.toMap(DynamicForm::getName, e -> String.valueOf(e.getValue())));
//        result.setConfig(collect);
        return result;
    }
//
//    /**
//     * 处理当前用户是否需要执行后续操作
//     *
//     * @param interfaceId 接口id
//     * @param mchId       商户id
//     */
//    @Override
//    public List<String> trailingOption(Long interfaceId, Long mchId) {
//        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
//                .eq(PayInterfaceConfig::getInterfaceId, interfaceId)
//                .eq(PayInterfaceConfig::getClientId, mchId)
//                .in(PayInterfaceConfig::getClientType, Arrays.asList(MchType.SUB_MERCHANT.code(), MchType.MERCHANT.code())));
//
//        Assert.isNull(payInterfaceConfig, ApiException.supplier(PayInterfaceConfigError.MERCHANT_NOT_CONFIG_PAY_INTERFACE));
//        TongLianOptionHandler option = (TongLianOptionHandler) payAgencyOptionContext.option(payInterfaceConfig.getPayingAgency());
//        return option.option(payInterfaceConfig);
//    }
//
    /**
     * 获取通联服务商与商家配置
     *
     * @param mchId       商家id
     * @param interfaceId 接口id
     */
    @Override
    public TongLianIsvAndMchConfigDAO getTongLianIsvAndMchConfig(Long mchId, Long interfaceId) {
        MchInfoVO mchInfo = mchInfoApi.mchInfo(mchId);
        Assert.isNull(mchInfo, ApiException.supplier(MchError.MCH_NOT_FOUND));
        Assert.isFalse(mchInfo.getType().equals(MchType.SUB_MERCHANT.code()), ApiException.supplier(MchError.MCH_TYPE_ERROR));
        // 获取当前商户配置
        PayInterfaceConfigVO mchInterfaceConfig = getConfigInfo(mchInfo.getId(), PayingAgency.ALL_IN, interfaceId);
        Assert.isNull(mchInterfaceConfig, ApiException.supplier(PayInterfaceConfigError.MERCHANT_NOT_CONFIG_PAY_INTERFACE));
        // 获取服务商配置信息
        PayInterfaceConfigVO isvInterfaceConfig = getConfigInfo(mchInfo.getIsvId(), PayingAgency.ALL_IN, interfaceId);
        Assert.isNull(isvInterfaceConfig, ApiException.supplier(PayInterfaceConfigError.ISV_NOT_CONFIG_PAY_INTERFACE));

        // 解析配置信息
        List<DynamicForm> mchDynamicForms = JsonUtil.parseArray(mchInterfaceConfig.getInterfaceParams(), DynamicForm.class);
        Map<String, Object> mch = mchDynamicForms.stream().collect(Collectors.toMap(DynamicForm::getName, DynamicForm::getValue));
        TongLianMchConfigDAO tongLianMchConfigDAO = JsonUtil.parse(JsonUtil.toJson(mch), TongLianMchConfigDAO.class);
        List<DynamicForm> isvDynamicForms = JsonUtil.parseArray(isvInterfaceConfig.getInterfaceParams(), DynamicForm.class);
        Map<String, Object> isv = isvDynamicForms.stream().collect(Collectors.toMap(DynamicForm::getName, DynamicForm::getValue));
        TongLianIsvConfigDAO tongLianIsvConfigDAO = JsonUtil.parse(JsonUtil.toJson(isv), TongLianIsvConfigDAO.class);
        return new TongLianIsvAndMchConfigDAO(tongLianIsvConfigDAO, tongLianMchConfigDAO);
    }

    /**
     * 获取服务商 支付配置列表
     *
     * @param isvId                  服务商id
     * @param payInterfaceDefineList 支付接口定义列表
     * @param clientType             客户端类型
     */
    @Override
    public List<PayInterfaceConfigListVO> getIsvInterfaceConfigList(Long isvId, List<PayInterfaceDefineListVO> payInterfaceDefineList, PayClientType clientType) {
        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientType, clientType.code())
                .eq(PayInterfaceConfig::getClientId, isvId)
        );
        return function.apply(payInterfaceDefineList, payInterfaceConfigList);
    }

    /**
     * 根据接口code 以及 商户渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    @Override
    public IsvInterfaceConfigVO isvInterfaceConfig(String interfaceCode, String mchChannelUser) {
        PayInterfaceConfig payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getMchChannelUser, mchChannelUser)
                .eq(PayInterfaceConfig::getInterfaceCode, interfaceCode)
        );
        if (ObjectUtils.isEmpty(payInterfaceConfig)) {
            return null;
        }
        payInterfaceConfig = payInterfaceConfigMapper.selectOne(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getClientId, payInterfaceConfig.getParentClientId())
                .eq(PayInterfaceConfig::getInterfaceCode, interfaceCode)
        );
        IsvInterfaceConfigVO result = PayInterfaceConfigConvert.INSTANCE.toIsvInterfaceConfigVO(payInterfaceConfig);
        String interfaceParam = payInterfaceConfig.getInterfaceParams();
        // TODO 解析接口参数
//        List<DynamicForm> dynamicFormList = JsonUtil.parseArray(interfaceParam, DynamicForm.class);
//        Map<String, String> collect = dynamicFormList.stream().collect(Collectors.toMap(DynamicForm::getName, e -> String.valueOf(e.getValue())));
//        result.setConfig(collect);
        return result;
    }

    private BiFunction<List<PayInterfaceDefineListVO>, List<PayInterfaceConfig>, List<PayInterfaceConfigListVO>> function = (payInterfaceDefineList, payInterfaceConfigList) -> {
        var let = new Object() {
            Map<Long, PayInterfaceConfig> map = new HashMap<>();
        };
        if (!CollectionUtils.isEmpty(payInterfaceConfigList)) {
            let.map = StreamBuild.of(payInterfaceConfigList).toMap(PayInterfaceConfig::getInterfaceId, e -> e);

        }
        return payInterfaceDefineList.stream().map(PayInterfaceConfigConvert.INSTANCE::toPayInterfaceConfigListVO)
                .peek(e -> {
                    if (let.map.containsKey(e.getId())) {
                        e.setEnable(Boolean.TRUE);
                    }
                })
                .toList();
    };


}