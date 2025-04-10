package com.baosight.payment.system.controller.mch;

import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.PayInterfaceConfigDTO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.saas.context.SystemUserContext;
import com.baosight.utils.enums.IBaseEnum;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

/**
 * 商户支付配置控制器
 *
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(SYSTEM + "/mch/pay/isv/config/manage")
public class MchPayIsvInterfaceConfigController {
    private final PayInterfaceConfigService payInterfaceConfigService;
    @Resource
    private MchInfoApi mchInfoApi;

    /**
     * 获取服务支付接口配置信息
     *
     * @param interfaceId 支付接口id
     * @return 服务商支付接口配置详情
     */
    @GetMapping("/{interfaceId}")
    public PayInterfaceConfigVO getIsvConfigInfo(@PathVariable(value = "interfaceId") Long interfaceId) {
        // TODO 当前默认服务商 后期需要进行变更
        Long companyId = SystemUserContext.getCompanyId();
        return payInterfaceConfigService.getConfigInfo(PayClientType.SERVICE_PROVIDER, companyId, interfaceId);
    }


    /**
     * 保存或更新支付配置
     *
     * @param payInterfaceConfigDTO 支付配置请求体
     */
    @PostMapping("/{mchId}")
    public Boolean saveOrUpdate(@RequestBody @Validated PayInterfaceConfigDTO payInterfaceConfigDTO, @PathVariable Long mchId) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        PayClientType payClientType = IBaseEnum.getByCode(PayClientType.class, mchInfoVO.getType());
        return payInterfaceConfigService.payConfigurationSaveOrUpdate(payInterfaceConfigDTO, payClientType, mchInfoVO.getId(), Boolean.TRUE);
        // 处理绑定信息

    }

    /**
     * 获取服务商支付配置列表
     */
    @GetMapping("/list/{isvId}")
    public List<PayInterfaceConfigListVO> getInterfaceConfigList(@PathVariable(value = "isvId") Long isvId) {
        return payInterfaceConfigService.getIsvInterfaceConfigList(isvId);
    }

}
