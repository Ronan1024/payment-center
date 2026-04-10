package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.channel.api.ChannelInfoApi;
import com.baosight.payment.channel.dto.resp.ChannelInfoRespDTO;
import com.baosight.payment.enums.MchType;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.isv.api.IsvInfoApi;
import com.baosight.payment.isv.vo.IsvInfoVO;
import com.baosight.payment.system.convert.PayInterfaceDefineConvert;
import com.baosight.payment.system.dao.entity.SystemClientChannelPermission;
import com.baosight.payment.system.dao.manager.PayInterFaceDefineManager;
import com.baosight.payment.system.dao.manager.PayWayManager;
import com.baosight.payment.system.dao.manager.SystemClientChannelPermissionManager;
import com.baosight.payment.system.error.PayInterfaceError;
import com.baosight.payment.system.mapper.PayInterfaceConfigMapper;
import com.baosight.payment.system.mapper.PayInterfaceDefineMapper;
import com.baosight.payment.system.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.dto.req.PayInterFaceDefineReqDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientPayChannelDefineRespDTO;
import com.baosight.payment.system.pojo.dto.resp.PayingChannelDefineListRespDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.system.service.PayWayService;
import com.baosight.payment.system.service.SystemClientChannelDefineService;
import com.baosight.payment.system.utils.DynamicFormUtil;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.saas.entity.DynamicForm;
import com.baosight.utils.json.JsonUtil;
import com.baosight.web.core.exception.ApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ronan.common.enums.IBaseEnum;
import com.ronan.common.utils.Assert;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.baosight.payment.system.error.PayInterfaceConfigError.CLIENT_GET_CHANNEL_CONFIG_ERROR;
import static com.baosight.payment.system.error.PayInterfaceError.*;
import static com.baosight.payment.system.error.PayWayError.PAY_WAY_NOT_FOUND;

/**
 *
 * @author longjiangran
 * @description 针对表【pay_interface_define(支付接口定义表)】的数据库操作Service实现
 * @createDate 2025-01-17 16:12:49
 */
@Service
@RequiredArgsConstructor
public class SystemClientChannelDefineServiceImpl extends ServiceImpl<PayInterfaceDefineMapper, PayInterfaceDefine> implements SystemClientChannelDefineService {
    private final PayInterfaceDefineMapper payInterfaceDefineMapper;
    private final PayInterFaceDefineManager payInterFaceDefineManager;
    private final PayInterfaceConfigMapper payInterfaceConfigMapper;
    private final SystemClientChannelPermissionManager systemClientChannelPermissionManager;
    private final PayWayManager payWayManager;

    private final ChannelInfoApi channelInfoApi;
    @Resource
    private MchInfoApi mchInfoApi;
    @Resource
    private IsvInfoApi isvInfoApi;

    private final PayWayService payWayService;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 保存支付通道的配置定义信息
     *
     * @param payInterFaceDefine 支付接口配置
     */
    @Override
    public Boolean insert(PayInterFaceDefineReqDTO payInterFaceDefine) {

        ChannelInfoRespDTO info = channelInfoApi.info(payInterFaceDefine.getCode());

        PayInterfaceDefine interfaceDefine = payInterFaceDefineManager.lambdaQuery()
                .eq(PayInterfaceDefine::getCode, info.getChannelCode()).one();
        Assert.notNull(interfaceDefine, ApiException.supplier(CHANNEL_CODE_HAS_DEFINED_CONFIGURATION));
        payInterFaceDefine.verify();
        List<PayWay> list = payWayManager.lambdaQuery().in(PayWay::getId, payInterFaceDefine.getPayWay()).list();
        Assert.isFalse(list.size() == payInterFaceDefine.getPayWay().size(), ApiException.supplier(PAY_WAY_NOT_FOUND));

        PayInterfaceDefine save = PayInterfaceDefineConvert.INSTANCE.toPayInterfaceDefine(payInterFaceDefine);
        save.setName(info.getChannelName());
        String payWay = payInterFaceDefine.getPayWay().stream().map(String::valueOf).collect(Collectors.joining(","));
        save.setPayWay(payWay);
        if (Boolean.TRUE.equals(payInterFaceDefine.getHasIsvMch())) {
            save.setIsvParams(JsonUtil.toJson(payInterFaceDefine.getIsvParams()));
            save.setIsvSubMchParams(JsonUtil.toJson(payInterFaceDefine.getIsvSubMchParams()));
        }
        if (Boolean.TRUE.equals(payInterFaceDefine.getHasMch())) {
            save.setNormalMchParams(JsonUtil.toJson(payInterFaceDefine.getNormalMchParams()));
        }
//        // 判断配置json参数是否符合格式
//        checkJsonParams(payInterFaceDefine.getIsvParams());
//        checkJsonParams(payInterFaceDefine.getIsvSubMchParams());
//        checkJsonParams(payInterFaceDefine.getNormalMchParams());
//        // 参数校验
//        verify(payInterFaceDefine);

        // 校验支付机构
//        Long count = payInterfaceDefineMapper.selectCount(new LambdaQueryWrapper<PayInterfaceDefine>()
//                .eq(PayInterfaceDefine::getCode, payInterFaceDefine.getCode())
//                .or()
//                .eq(PayInterfaceDefine::getName,payInterFaceDefine.getCode()));
//        Assert.isTrue(count > 0, () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NAME_EXIST));
//        String payWay = payInterFaceDefine.getPayWay();  // 多种支付方式之间用逗号分割
//        if(StringUtils.hasText(payWay)) {
//            List<PayWay> payWays = payWayService.listByIds(Arrays.stream(payWay.split(",")).toList());
//            save.setPayingAgency(payWays.get(0).getPayingAgency());
//            List<Integer> payingClientList = payWays.stream().map(PayWay::getPayingClient).toList();
//            save.setScenario(payingClientList.toString());
//        }
        save.setCreateBy(UserContext.INSTANCE.userId());
        return payInterFaceDefineManager.save(save);
    }

    /**
     * 参数校验
     *
     * @param payInterfaceDefine
     */
    @Deprecated
    private void verify(PayInterFaceDefineDTO payInterfaceDefine) {
        Assert.isTrue(payInterfaceDefine.getHasMch() && !StringUtils.hasText(payInterfaceDefine.getNormalMchParams()), () -> new ApiException(PAY_INTERFACE_NORMAL_MCH_PARAMS_NULL));
        Assert.isTrue(payInterfaceDefine.getHasIsvMch() && !StringUtils.hasText(payInterfaceDefine.getIsvSubMchParams()), () -> new ApiException(PayInterfaceError.PAY_INTERFACE_ISV_SUB_MCH_PARAMS_NULL));
        Assert.isTrue(payInterfaceDefine.getHasIsvMch() && !StringUtils.hasText(payInterfaceDefine.getIsvParams()), () -> new ApiException(PAY_INTERFACE_ISV_PARAMS_NULL));
        if (!StringUtils.hasText(payInterfaceDefine.getNormalMchParams())) {
            payInterfaceDefine.setNormalMchParams(null);
        }
        if (!StringUtils.hasText(payInterfaceDefine.getIsvSubMchParams())) {
            payInterfaceDefine.setIsvSubMchParams(null);
        }
        if (!StringUtils.hasText(payInterfaceDefine.getIsvParams())) {
            payInterfaceDefine.setIsvParams(null);
        }

    }


    /**
     * 检查json串是否符合格式
     *
     * @param params
     * @return
     */
    @Deprecated
    private void checkJsonParams(String params) {
        // 入参非空判断
        if (params == null || params.trim().isEmpty()) {
            return;
        }

        try {
            // 核心：使用TypeReference指定泛型类型（解决泛型擦除问题）
            objectMapper.readValue(params, new TypeReference<List<DynamicForm>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("参数格式与Param类不匹配");
        }
    }


    /**
     * 更新支付接口
     *
     * @param payInterFaceDefineDTO 支付接口更新信息
     * @param id
     */
    @Override
    public Boolean updatePayInterface(PayInterFaceDefineReqDTO payInterFaceDefineDTO, Long id) {

        PayInterfaceDefine payInterfaceDefine = payInterFaceDefineManager.getById(id);
        Assert.isNull(payInterfaceDefine, ApiException.supplier(PAY_INTERFACE_NOT_EXIST));
        List<PayWay> list = payWayManager.lambdaQuery().in(PayWay::getId, payInterFaceDefineDTO.getPayWay()).list();
        Assert.isFalse(list.size() == payInterFaceDefineDTO.getPayWay().size(), ApiException.supplier(PAY_WAY_NOT_FOUND));
        payInterFaceDefineDTO.verify();
//
//        // 查询已经签约当前接口的客户端类型
//        List<Integer> signedClientTypeList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
//                        .eq(PayInterfaceConfig::getInterfaceId, payInterFaceDefineDTO.getId()))
//                .stream().map(PayInterfaceConfig::getClientType).toList();
//
//        if (signedClientTypeList.contains(PayClientType.MERCHANT.code()) && !payInterFaceDefineDTO.getHasMch()) {
//            throw new IllegalStateException("存在已签约的商户！");
//        }
//        if ((signedClientTypeList.contains(PayClientType.SERVICE_PROVIDER.code()) || signedClientTypeList.contains(PayClientType.SUB_MERCHANT.code())) && !payInterFaceDefineDTO.getHasIsvMch()) {
//            throw new IllegalStateException("存在已签约的服务商或特约商户！");
//        }

        PayInterfaceDefineConvert.INSTANCE.copyPayInterfaceDefine(payInterfaceDefine, payInterFaceDefineDTO);
        payInterfaceDefine.setUpdateBy(UserContext.INSTANCE.userId());
        payInterfaceDefine.setPayWay(payInterFaceDefineDTO.getPayWay().stream().map(String::valueOf).collect(Collectors.joining(",")));
        if (Boolean.TRUE.equals(payInterFaceDefineDTO.getHasIsvMch())) {
            payInterfaceDefine.setIsvParams(JsonUtil.toJson(payInterFaceDefineDTO.getIsvParams()));
            payInterfaceDefine.setIsvSubMchParams(JsonUtil.toJson(payInterFaceDefineDTO.getIsvSubMchParams()));
        }
        if (Boolean.TRUE.equals(payInterFaceDefineDTO.getHasMch())) {
            payInterfaceDefine.setNormalMchParams(JsonUtil.toJson(payInterFaceDefineDTO.getNormalMchParams()));
        }
        return payInterFaceDefineManager.updateById(payInterfaceDefine);
//
//
//        boolean update = payInterfaceDefineMapper.updateById(payInterfaceDefine) > 0;
//        // 修改已签约的支付方式
//        if (update) {
//            payInterfaceConfigMapper.update(new LambdaUpdateWrapper<PayInterfaceConfig>()
//                    .eq(PayInterfaceConfig::getInterfaceId, payInterfaceDefine.getId())
//                    .set(PayInterfaceConfig::getPayWay, payInterfaceDefine.getPayWay())
//            );
//        }

//        return Boolean.TRUE;
    }

    /**
     * 获取支付接口分页列表
     */
    @Override
    public PageResponse<PayInterfaceDefineListVO> payInterfacePage(PayInterfaceListDTO pageDTO) {
        PageUtil<PayInterfaceDefineListVO> pageUtil = new PageUtil<PayInterfaceDefineListVO>(pageDTO);
        return pageUtil.builder(payInterfaceDefineMapper.page(pageUtil.Page(), pageDTO)).build();
    }

    /**
     * 获取支付接口定义详情
     *
     * @param id 支付接口id
     */
    @Override
    public PayInterfaceDefineVO detail(Long id) {
        PayInterfaceDefineVO interfaceById = payInterfaceDefineMapper.getInterfaceById(id);
        List<PayWay> list = payWayService.list(new LambdaQueryWrapper<PayWay>().in(PayWay::getId, interfaceById.getPayWay()));
//        interfaceById.setPayWayId(list.stream().map(PayWay::getPayName).collect(Collectors.joining(",")));
        interfaceById.setScenario(list.stream().map(e -> String.valueOf(e.getPayingClient())).collect(Collectors.joining(",")));
        return interfaceById;
    }

    /**
     * 删除支付接口定义信息
     *
     * @param id
     */
    @Override
    public Boolean delete(Long id) {
        // 校验该支付方式是否有服务商或商户签约
        List<PayInterfaceConfig> payInterfaceConfigs = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>().eq(PayInterfaceConfig::getInterfaceId, id));
        Assert.isTrue(!payInterfaceConfigs.isEmpty(), "该支付接口已有服务商或商户签约，无法删除！");

        payInterfaceDefineMapper.deleteById(id);
        return Boolean.TRUE;
    }

    /**
     * 获取支付接口指定客户端列表信息
     */
    @Override
    public List<PayInterfaceDefineListVO> getPayInterfaceDefineList(PayClientType payClientType) {
        LambdaQueryWrapper<PayInterfaceDefine> queryWrapper = new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE);
        consumer.accept(queryWrapper, payClientType);
        List<PayInterfaceDefine> payInterfaceDefineList = payInterfaceDefineMapper.selectList(queryWrapper);
        return payInterfaceDefineList.stream().map(PayInterfaceDefineConvert.INSTANCE::toPayInterfaceDefineListVO).toList();
    }


    /**
     * 获取支付接口指定客户端列表信息
     *
     * @param payClientType
     * @param interfaceIdList
     */
    @Override
    public List<PayInterfaceDefineListVO> getPayInterfaceDefineList(PayClientType payClientType, List<Long> interfaceIdList) {
        LambdaQueryWrapper<PayInterfaceDefine> queryWrapper = new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE)
                .in(PayInterfaceDefine::getId, interfaceIdList);

        consumer.accept(queryWrapper, payClientType);
        List<PayInterfaceDefine> payInterfaceDefineList = payInterfaceDefineMapper.selectList(queryWrapper);
        return payInterfaceDefineList.stream().map(PayInterfaceDefineConvert.INSTANCE::toPayInterfaceDefineListVO).toList();
    }

    private final BiConsumer<LambdaQueryWrapper<PayInterfaceDefine>, PayClientType> consumer = (queryWrapper, payClient) -> {
        if (payClient.equals(PayClientType.SERVICE_PROVIDER) || payClient.equals(PayClientType.SUB_MERCHANT)) {
            queryWrapper.eq(PayInterfaceDefine::getHasIsvMch, Boolean.TRUE);
        } else {
            queryWrapper.eq(PayInterfaceDefine::getHasMch, Boolean.TRUE);
        }
    };

    /**
     * 获取服务商可以用的支付接口定义列表
     */
    @Override
    public List<PayInterfaceDefineListVO> getPayInterfaceDefineListByISV() {
        List<PayInterfaceDefine> payInterfaceDefineList = payInterfaceDefineMapper.selectList(new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getHasIsvMch, Boolean.TRUE)
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE)
        );
        return payInterfaceDefineList.stream().map(PayInterfaceDefineConvert.INSTANCE::toPayInterfaceDefineListVO).toList();
    }

    /**
     * 获取支付接口定义信息
     *
     * @param id 支付接口id
     * @return 支付接口新信息
     */
    @Override
    public PayInterfaceDefine payInterfaceDefineBy(Long id) {
        return payInterfaceDefineMapper.selectOne(new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getId, id));
    }

    /**
     * 获取可配置的接口定义列表
     *
     * @param mchId 商户id
     */
    @Override
    public List<PayInterfaceDefineListVO> mchPayInterfaceDefineList(Long mchId) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        PayClientType payClientType = IBaseEnum.getByCode(PayClientType.class, mchInfoVO.getType());
        return getPayInterfaceDefineList(payClientType);
    }

    /**
     * 根据接口id 获取接口列表
     *
     * @param interfaceIdList 接口id
     */
    @Override
    public List<PayInterfaceDefine> payInterfaceDefineByIdList(List<Long> interfaceIdList) {
        return payInterfaceDefineMapper.selectList(new LambdaQueryWrapper<PayInterfaceDefine>()
                .in(PayInterfaceDefine::getId, interfaceIdList)
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE)
        );
    }

    /**
     * 获取支付接口定义信息
     *
     * @param code 支付接口code
     */
    @Override
    public PayInterfaceDefine payInterfaceDefineByCode(String code) {
        return payInterfaceDefineMapper.selectOne(new LambdaQueryWrapper<PayInterfaceDefine>()
                // .eq(PayInterfaceDefine::getEnable, Boolean.TRUE)
                .eq(PayInterfaceDefine::getCode, code)
        );
    }


    /**
     * 查询商户可以绑定的支付接口定义信息
     *
     * @param payClientType
     * @return
     */
    @Override
    public List<PayInterfaceDefineListVO> queryList(Integer payClientType) {
        List<PayInterfaceDefine> list = list(new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(payClientType.equals(PayClientType.MERCHANT.code()), PayInterfaceDefine::getHasMch, Boolean.TRUE)
                .eq(payClientType.equals(PayClientType.SERVICE_PROVIDER.code()) || payClientType.equals(PayClientType.SUB_MERCHANT.code()), PayInterfaceDefine::getHasIsvMch, Boolean.TRUE));
        return list.stream().map(PayInterfaceDefineConvert.INSTANCE::toPayInterfaceDefineListVO).toList();
    }

    /**
     * 获取支付通道列表
     */
    @Override
    public List<PayingChannelDefineListRespDTO> channelDefineList(Integer clientType, Long clientId) {
        // TODO 忽略已配置的信息
        if (clientType.equals(PayClientType.SERVICE_PROVIDER.code()) || clientType.equals(PayClientType.MERCHANT.code())) {
            // 服务商普通商家直接返回
            List<PayInterfaceDefine> list = payInterFaceDefineManager.lambdaQuery()
                    .eq(clientType.equals(PayClientType.SERVICE_PROVIDER.code()), PayInterfaceDefine::getHasIsvMch, Boolean.TRUE)
                    .eq(clientType.equals(PayClientType.MERCHANT.code()), PayInterfaceDefine::getHasMch, Boolean.TRUE)
                    .eq(PayInterfaceDefine::getEnable, Boolean.TRUE)
                    .list();
            return list.stream().map(PayInterfaceDefineConvert.INSTANCE::toPayingChannelDefineListRespDTO).toList();
        }

        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(clientId);
        List<SystemClientChannelPermission> list = systemClientChannelPermissionManager.lambdaQuery()
                .eq(SystemClientChannelPermission::getClientType, PayClientType.SERVICE_PROVIDER.code())
                .eq(SystemClientChannelPermission::getClientId, mchInfoVO.getIsvId()).list();

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }


        List<Long> channelDefineIds = list.stream().map(SystemClientChannelPermission::getChannelDefineId).toList();

        List<PayInterfaceDefine> payInterfaceDefines = payInterFaceDefineManager.lambdaQuery()
                .in(PayInterfaceDefine::getId, channelDefineIds)
                .list();
        return payInterfaceDefines.stream().map(PayInterfaceDefineConvert.INSTANCE::toPayingChannelDefineListRespDTO).toList();
    }

    /**
     * 修改支付通道启用状态
     *
     * @param id 支付通道id
     */
    @Override
    public void editEnable(Long id) {
        PayInterfaceDefine interfaceDefine = payInterFaceDefineManager.getById(id);
        Assert.isNull(interfaceDefine, ApiException.supplier(PAY_INTERFACE_DEFINE_NOT_EXIST));
        interfaceDefine.setEnable(!interfaceDefine.getEnable());
        payInterFaceDefineManager.updateById(interfaceDefine);
    }

    /**
     * 获取客户端支付渠道配置信息
     *
     * @param clientId  客户端id
     * @param type      客户端类型
     * @param channelId 渠道id
     */
    @Override
    public ClientPayChannelDefineRespDTO clientChannelDefine(Long clientId, Integer type, Long channelId) {
        PayInterfaceDefine interfaceDefine = payInterFaceDefineManager.getById(channelId);
        ChannelInfoRespDTO info = channelInfoApi.info(interfaceDefine.getCode());

        Assert.isNull(interfaceDefine, ApiException.supplier(PAY_INTERFACE_DEFINE_NOT_EXIST));
        ClientPayChannelDefineRespDTO result = new ClientPayChannelDefineRespDTO();
        String dynamicFormStr;
        if (MchType.SERVICER_MERCHANT.code().equals(type)) {
            // 处理服务商
            IsvInfoVO isvInfoVO = isvInfoApi.isvInfoById(clientId);
            Assert.isNull(isvInfoVO, ApiException.supplier(CLIENT_GET_CHANNEL_CONFIG_ERROR ));
            dynamicFormStr = interfaceDefine.getIsvParams();
        } else if (MchType.SUB_MERCHANT.code().equals(type)) {
            MchInfoVO mchInfoVO = mchInfoApi.mchInfo(clientId);
            Assert.isNull(mchInfoVO, ApiException.supplier(CLIENT_GET_CHANNEL_CONFIG_ERROR));
            Assert.isFalse(mchInfoVO.getType().equals(PayClientType.SUB_MERCHANT.code()), ApiException.supplier(CLIENT_GET_CHANNEL_CONFIG_ERROR));
            dynamicFormStr = interfaceDefine.getIsvSubMchParams();
        } else if (MchType.MERCHANT.code().equals(type)) {
            MchInfoVO mchInfoVO = mchInfoApi.mchInfo(clientId);
            Assert.isNull(mchInfoVO, ApiException.supplier(CLIENT_GET_CHANNEL_CONFIG_ERROR));
            dynamicFormStr = interfaceDefine.getNormalMchParams();
        } else {
            throw new ApiException(CLIENT_TYPE_ERROR);
        }
        List<DynamicFormUtil.DynamicForm> dynamicFormList = JsonUtil.parseArray(dynamicFormStr, DynamicFormUtil.DynamicForm.class);
        result.setDynamicForm(dynamicFormList);
        result.setChannelCode(interfaceDefine.getCode());
        result.setId(interfaceDefine.getId());
        result.setChannelName(info.getChannelName());
        return result;
    }


}




