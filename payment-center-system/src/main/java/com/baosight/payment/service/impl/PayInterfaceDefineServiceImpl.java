package com.baosight.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.convert.PayInterfaceDefineConvert;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.error.PayInterfaceError;
import com.baosight.payment.mapper.PayInterfaceDefineMapper;
import com.baosight.payment.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.pojo.dto.PayInterfacePageDTO;
import com.baosight.payment.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.service.PayInterfaceConfigService;
import com.baosight.payment.service.PayInterfaceDefineService;
import com.baosight.saas.context.AbstractUserContext;
import com.baosight.saas.context.SystemUserContext;
import com.baosight.saas.utils.DynamicFormParse;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.stream.StreamBuild;
import com.baosight.utils.utils.Assert;
import com.baosight.web.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_define(支付接口定义表)】的数据库操作Service实现
 * @createDate 2025-01-17 16:12:49
 */
@Service
@RequiredArgsConstructor
public class PayInterfaceDefineServiceImpl extends ServiceImpl<PayInterfaceDefineMapper, PayInterfaceDefine> implements PayInterfaceDefineService {
    private final PayInterfaceDefineMapper payInterfaceDefineMapper;
    private final PayInterfaceConfigService payInterfaceConfigService;


    /**
     * 新增支付接口参数配置
     *
     * @param payInterFaceDefine 支付接口配置
     */
    @Override
    public Boolean insert(PayInterFaceDefineDTO payInterFaceDefine) {
        Long count = payInterfaceDefineMapper.selectCount(new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getName, payInterFaceDefine.getName()));
        pauInterfaceVerify(payInterFaceDefine, count);
        PayInterfaceDefine save = PayInterfaceDefineConvert.INSTANCE.toPayInterfaceDefine(payInterFaceDefine);
        save.setCreateBy(SystemUserContext.getUserId());
        save.setPayWay(payInterFaceDefine.getPayWayList().stream().map(String::valueOf).collect(Collectors.joining(",")));
        if (!CollectionUtils.isEmpty(payInterFaceDefine.getFacilitatorParams())) {
            save.setIsvParams(JsonUtil.toJson(payInterFaceDefine.getFacilitatorParams()));
        }
        if (!CollectionUtils.isEmpty(payInterFaceDefine.getSubMchParams())) {
            save.setIsvSubMchParams(JsonUtil.toJson(payInterFaceDefine.getSubMchParams()));
        }
        if (!CollectionUtils.isEmpty(payInterFaceDefine.getNormalMchParams())) {
            save.setNormalMchParams(JsonUtil.toJson(payInterFaceDefine.getNormalMchParams()));
        }
        return payInterfaceDefineMapper.insert(save) > 0;
    }

    private void pauInterfaceVerify(PayInterFaceDefineDTO payInterFaceDefine, Long count) {
        Assert.isTrue(count > 0, () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NAME_EXIST));
        Assert.isTrue(payInterFaceDefine.getHasMch() && CollectionUtils.isEmpty(payInterFaceDefine.getNormalMchParams()), () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NORMAL_MCH_PARAMS_NULL));
        Assert.isTrue(payInterFaceDefine.getHasSubMch() && CollectionUtils.isEmpty(payInterFaceDefine.getSubMchParams()), () -> new ApiException(PayInterfaceError.PAY_INTERFACE_ISV_SUB_MCH_PARAMS_NULL));
        Assert.isNull(CollectionUtils.isEmpty(payInterFaceDefine.getFacilitatorParams()), () -> new ApiException(PayInterfaceError.PAY_INTERFACE_ISV_PARAMS_NULL));
    }

    /**
     * 更新支付接口
     *
     * @param id                    支付接口id
     * @param payInterFaceDefineDTO 支付接口更新信息
     */
    @Override
    public Boolean updatePayInterface(Long id, PayInterFaceDefineDTO payInterFaceDefineDTO) {
        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineMapper.selectById(id);
        Assert.isNull(payInterfaceDefine, () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NOT_EXIST));
        Long count = payInterfaceDefineMapper.selectCount(new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getName, payInterFaceDefineDTO.getName())
                .ne(PayInterfaceDefine::getId, id)
        );
        pauInterfaceVerify(payInterFaceDefineDTO, count);
        PayInterfaceDefineConvert.INSTANCE.copyPayInterfaceDefine(payInterfaceDefine, payInterFaceDefineDTO);
        payInterfaceDefine.setUpdateBy(AbstractUserContext.getUserId());
        payInterfaceDefine.setPayWay(payInterFaceDefineDTO.getPayWayList().stream().map(String::valueOf).collect(Collectors.joining(",")));
        if (!CollectionUtils.isEmpty(payInterFaceDefineDTO.getFacilitatorParams())) {
            payInterfaceDefine.setIsvParams(JsonUtil.toJson(payInterFaceDefineDTO.getFacilitatorParams()));
        }
        if (!CollectionUtils.isEmpty(payInterFaceDefineDTO.getSubMchParams())) {
            payInterfaceDefine.setIsvSubMchParams(JsonUtil.toJson(payInterFaceDefineDTO.getSubMchParams()));
        }
        if (!CollectionUtils.isEmpty(payInterFaceDefineDTO.getNormalMchParams())) {
            payInterfaceDefine.setNormalMchParams(JsonUtil.toJson(payInterFaceDefineDTO.getNormalMchParams()));
        }
        return payInterfaceDefineMapper.updateById(payInterfaceDefine) > 0;
    }

    /**
     * 获取支付接口分页列表
     */
    @Override
    public List<PayInterfaceDefineListVO> payInterfacePage(PayInterfacePageDTO pageDTO) {
        List<PayInterfaceDefine> payInterfaceDefine = payInterfaceDefineMapper.selectList(new LambdaQueryWrapper<PayInterfaceDefine>()
                .like(StringUtils.hasText(pageDTO.getName()), PayInterfaceDefine::getName, pageDTO.getName())
        );
        return payInterfaceDefine.stream().map(e -> {
            PayInterfaceDefineListVO payInterfaceDefineListVO = new PayInterfaceDefineListVO();
            payInterfaceDefineListVO.setId(e.getId());
            payInterfaceDefineListVO.setName(e.getName());
            payInterfaceDefineListVO.setRemark(e.getRemark());
            return payInterfaceDefineListVO;
        }).toList();
    }

    /**
     * 获取支付接口定义详情
     *
     * @param id 支付接口id
     */
    @Override
    public PayInterfaceDefineVO detail(Long id) {
        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineMapper.selectById(id);
        Assert.isNull(payInterfaceDefine, () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NOT_EXIST));
        PayInterfaceDefineVO result = PayInterfaceDefineConvert.INSTANCE.toPayInterfaceDefineVO(payInterfaceDefine);
        result.setPayWayList(Arrays.stream(payInterfaceDefine.getPayWay().split(",")).toList());
        if (StringUtils.hasText(payInterfaceDefine.getIsvParams())) {
            result.setFacilitatorParams(DynamicFormParse.convert(payInterfaceDefine.getIsvParams()));
        }
        if (StringUtils.hasText(payInterfaceDefine.getIsvSubMchParams())) {
            result.setSubMchParams(DynamicFormParse.convert(payInterfaceDefine.getIsvSubMchParams()));
        }
        if (StringUtils.hasText(payInterfaceDefine.getNormalMchParams())) {
            result.setNormalMchParams(DynamicFormParse.convert(payInterfaceDefine.getNormalMchParams()));
        }
        return result;
    }

    /**
     * 删除支付接口定义信息
     *
     * @param id
     */
    @Override
    public Boolean delete(Long id) {
        // TODO 校验该支付方式是否有服务商或商户配置参数或者已有订单
//        if (payInterfaceConfigService.count(PayInterfaceConfig.gw().eq(PayInterfaceConfig::getIfCode, ifCode)) > 0
//                || payOrderService.count(PayOrder.gw().eq(PayOrder::getIfCode, ifCode)) > 0) {
//            throw new BizException("该支付接口已有服务商或商户配置参数或已发生交易，无法删除！");
//        }
        PayInterfaceDefine payInterfaceDefine = payInterfaceDefineMapper.selectById(id);
        Assert.isNull(payInterfaceDefine, () -> new ApiException(PayInterfaceError.PAY_INTERFACE_NOT_EXIST));
        return payInterfaceDefineMapper.deleteById(id) > 0;
    }

    /**
     * 获取支付接口指定客户端列表信息
     *
     * @param payClientType 客户端常量
     * @param clientInfoId  客户端唯一id
     */
    @Override
    public List<PayInterfaceDefineListVO> getPayInterfaceDefineList(PayClientType payClientType, Long clientInfoId) {
        List<PayInterfaceDefine> payInterfaceDefineList = payInterfaceDefineMapper.selectList(new LambdaQueryWrapper<PayInterfaceDefine>()
                .eq(PayInterfaceDefine::getHasIsvMch, Boolean.TRUE)
                .eq(PayInterfaceDefine::getEnable, Boolean.TRUE)
        );
        List<PayInterfaceDefineListVO> result = payInterfaceDefineList.stream().map(PayInterfaceDefineConvert.INSTANCE::toPayInterfaceDefineListVO).toList();
        // 获取支付参数信息
        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigService.getPayConfiguration(payClientType, clientInfoId);
        if (!CollectionUtils.isEmpty(payInterfaceConfigList)) {
            Map<Long, PayInterfaceConfig> payInterfaceConfigMap = StreamBuild.of(payInterfaceConfigList).toMap(PayInterfaceConfig::getInterfaceId, e -> e);
            result.forEach(e -> {
                if (payInterfaceConfigMap.containsKey(e.getId())) {
                    e.setEnable(payInterfaceConfigMap.get(e.getId()).getEnable());
                } else {
                    e.setEnable(Boolean.FALSE);
                }
            });

        }

        return result;
    }


}




