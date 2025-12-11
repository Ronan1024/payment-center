package com.baosight.payment.system.controller.system;

import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.error.MchError;
import com.baosight.payment.system.pojo.dto.ClientPayInterfaceConfigDTO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.utils.utils.Assert;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.enums.IBaseEnum;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.LongFunction;


/**
 * @program: payment-center
 * @description: 平台管理商户支付接口配置
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@RestController
@RequiredArgsConstructor
//@RequestMapping(SYSTEM + "/pm/pay/mch/config/manage")
@RequestMapping("/pm/pay/mch/config/manage")
public class SystemPayMchInterfaceConfigController {
    private final PayInterfaceConfigService payInterfaceConfigService;
    @Resource
    private MchInfoApi mchInfoApi;


    /**
     * 获取服务支付接口配置信息
     *
     * @param id          商户id
     * @param interfaceId 支付接口id
     * @return 服务商支付接口配置详情
     */
    @GetMapping("/{id}/{interfaceId}")
    public PayInterfaceConfigVO getIsvConfigInfo(@PathVariable(value = "id") Long id, @PathVariable(value = "interfaceId") Long interfaceId) {
        PayClientType payClientType = function.apply(id);
        return payInterfaceConfigService.getConfigInfo(payClientType, id, interfaceId);
    }


    /**
     * 保存或更新支付配置
     *
     * @param payInterfaceConfigDTO 支付配置请求体
     */
    @PostMapping
    public Boolean saveOrUpdate(@RequestBody @Validated ClientPayInterfaceConfigDTO payInterfaceConfigDTO) {
        PayClientType payClientType = function.apply(payInterfaceConfigDTO.getClientId());
        return payInterfaceConfigService.payConfigurationSaveOrUpdate(payInterfaceConfigDTO, payClientType, payInterfaceConfigDTO.getClientId(), Boolean.TRUE);
    }

    /**
     * 获取商户支付配置列表
     */
    @GetMapping("/list/{mchId}")
    public List<PayInterfaceConfigListVO> getInterfaceConfigList(@PathVariable(value = "mchId") Long mchId) {
        return payInterfaceConfigService.getMchInterfaceConfigList(mchId);
    }

    /**
     * 根据商户id 获取商户类型
     */
    private final LongFunction<PayClientType> function = mchId -> {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        Assert.isNull(mchInfoVO, ApiException.supplier(MchError.MCH_NOT_FOUND));
        return IBaseEnum.getByCode(PayClientType.class, mchInfoVO.getType());
    };
}
