package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.convert.PayInterfaceDefineConvert;
import com.baosight.payment.system.error.PayInterfaceError;
import com.baosight.payment.system.mapper.PayInterfaceConfigMapper;
import com.baosight.payment.system.mapper.PayInterfaceDefineMapper;
import com.baosight.payment.system.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.entity.Param;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.utils.utils.Assert;
import com.baosight.web.core.exception.ApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.ronan.common.enums.IBaseEnum;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_define(支付接口定义表)】的数据库操作Service实现
 * @createDate 2025-01-17 16:12:49
 */
@Service
@RequiredArgsConstructor
public class PayInterfaceDefineServiceImpl extends ServiceImpl<PayInterfaceDefineMapper, PayInterfaceDefine> implements PayInterfaceDefineService {
    private final PayInterfaceDefineMapper payInterfaceDefineMapper;
    @Resource
    private MchInfoApi mchInfoApi;
    private final PayInterfaceConfigMapper payInterfaceConfigMapper;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 新增支付接口参数配置
     *
     * @param payInterFaceDefine 支付接口配置
     */
    @Override
    public Boolean insert(PayInterFaceDefineDTO payInterFaceDefine) {
        // 判断配置json参数是否符合格式
        checkJsonParams(payInterFaceDefine.getIsvParams());
        checkJsonParams(payInterFaceDefine.getIsvSubMchParams());
        checkJsonParams(payInterFaceDefine.getNormalMchParams());

        Long count = payInterfaceDefineMapper.selectCount(new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getCode, payInterFaceDefine.getCode()));
        if (count > 0) {
            throw new ApiException(PayInterfaceError.PAY_INTERFACE_NAME_EXIST);
        }

        PayInterfaceDefine save = PayInterfaceDefineConvert.INSTANCE.toPayInterfaceDefine(payInterFaceDefine);
//        save.setCreateBy(UserContext.INSTANCE.getUserId());
        return payInterfaceDefineMapper.insert(save) > 0;
    }

    /**
     * 检查json串是否符合格式
     * @param params
     * @return
     */
    private void checkJsonParams(String params){
        // 入参非空判断
        if (params == null || params.trim().isEmpty()) {
           return;
        }

        try {
            // 核心：使用TypeReference指定泛型类型（解决泛型擦除问题）
            objectMapper.readValue(params, new TypeReference<List<Param>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("参数格式与Param类不匹配");
        }
    }


    /**
     * 更新支付接口
     *
     * @param payInterFaceDefineDTO 支付接口更新信息
     */
    @Override
    public Boolean updatePayInterface(PayInterFaceDefineDTO payInterFaceDefineDTO) {
        // 判断配置json参数是否符合格式
        checkJsonParams(payInterFaceDefineDTO.getIsvParams());
        checkJsonParams(payInterFaceDefineDTO.getIsvSubMchParams());
        checkJsonParams(payInterFaceDefineDTO.getNormalMchParams());

        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineMapper.selectById(payInterFaceDefineDTO.getId());
        Assert.isNull(payInterfaceDefine, () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NOT_EXIST));
        // 查询已经签约当前接口的客户端类型
        List<Integer> signedClientTypeList = payInterfaceConfigMapper.selectList(new LambdaQueryWrapper<PayInterfaceConfig>()
                .eq(PayInterfaceConfig::getInterfaceId, payInterFaceDefineDTO.getId()))
                .stream().map(PayInterfaceConfig::getClientType).toList();

        if(signedClientTypeList.contains(PayClientType.MERCHANT.code()) && !payInterFaceDefineDTO.getHasMch()){
            throw new IllegalStateException("存在已签约的商户！");
        }

        if((signedClientTypeList.contains(PayClientType.SERVICE_PROVIDER.code()) || signedClientTypeList.contains(PayClientType.SUB_MERCHANT.code())) && !payInterFaceDefineDTO.getHasIsvMch()){
            throw new IllegalStateException("存在已签约的服务商或特约商户！");
        }

        PayInterfaceDefineConvert.INSTANCE.copyPayInterfaceDefine(payInterfaceDefine, payInterFaceDefineDTO);
//        payInterfaceDefine.setUpdateBy(UserContext.INSTANCE.getUserId());
        boolean update = payInterfaceDefineMapper.updateById(payInterfaceDefine) > 0;
        // 修改已签约的支付方式
        if (update) {
            payInterfaceConfigMapper.update(new LambdaUpdateWrapper<PayInterfaceConfig>()
                    .eq(PayInterfaceConfig::getInterfaceId, payInterfaceDefine.getId())
                    .set(PayInterfaceConfig::getPayWay, payInterfaceDefine.getPayWay())
            );
        }

        return Boolean.TRUE;
    }

    /**
     * 获取支付接口分页列表
     */
    @Override
    public PageResponse<PayInterfaceDefineListVO> payInterfacePage(PayInterfaceListDTO pageDTO) {
        PageUtil<PayInterfaceDefineListVO> pageUtil = new PageUtil<PayInterfaceDefineListVO>(pageDTO);
        return pageUtil.builder(payInterfaceDefineMapper.page(pageUtil.Page(),pageDTO)).build();
    }

    /**
     * 获取支付接口定义详情
     *
     * @param id 支付接口id
     */
    @Override
    public PayInterfaceDefineVO detail(Long id) {
        return payInterfaceDefineMapper.getInterfaceById(id);
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
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE)
                .eq(PayInterfaceDefine::getCode, code)
        );
    }


}




