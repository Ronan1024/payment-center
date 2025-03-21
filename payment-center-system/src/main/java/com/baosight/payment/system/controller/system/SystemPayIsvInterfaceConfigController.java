package com.baosight.payment.system.controller.system;

import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.ClientPayInterfaceConfigDTO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

/**
 * 系统服务商支付配置控制器
 *
 * @author L.J.Rab
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(SYSTEM + "/pm/pay/isv/config/manage")
public class SystemPayIsvInterfaceConfigController {
    private final PayInterfaceConfigService payInterfaceConfigService;


    /**
     * 获取服务支付接口配置信息
     *
     * @param id          服务商id
     * @param interfaceId 支付接口id
     * @return 服务商支付接口配置详情
     */
    @GetMapping("/{id}/{interfaceId}")
    public PayInterfaceConfigVO getIsvConfigInfo(@PathVariable(value = "id") Long id, @PathVariable(value = "interfaceId") Long interfaceId) {
        return payInterfaceConfigService.getConfigInfo(PayClientType.SERVICE_PROVIDER, id, interfaceId);
    }


    /**
     * 保存或更新支付配置
     *
     * @param payInterfaceConfigDTO 支付配置请求体
     */
    @PostMapping
    public Boolean saveOrUpdate(@RequestBody @Validated ClientPayInterfaceConfigDTO payInterfaceConfigDTO) {
        return payInterfaceConfigService.payConfigurationSaveOrUpdate(payInterfaceConfigDTO, PayClientType.SERVICE_PROVIDER, payInterfaceConfigDTO.getClientId(), Boolean.FALSE);
    }

    /**
     * 获取服务商支付配置列表
     */
    @GetMapping("/list/{isvId}")
    public List<PayInterfaceConfigListVO> getInterfaceConfigList(@PathVariable(value = "isvId") Long isvId) {
        return payInterfaceConfigService.getIsvInterfaceConfigList(isvId);
    }

}
