package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.app.PayInterfaceConfigAppService;
import com.baosight.security.annotation.AllowAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统服务商支付配置管理
 *
 * @author L.J.Rab
 */
@RestController
@RequiredArgsConstructor
//@RequestMapping(SYSTEM + "/pm/pay/isv/config/manage")
@AllowAccess
@RequestMapping("/pm/pay/isv/config/manage")
public class SystemPayIsvInterfaceConfigController {
    private final PayInterfaceConfigService payInterfaceConfigService;
    private final PayInterfaceConfigAppService payInterfaceConfigAppService;


//    /**
//     * 获取服务支付接口配置信息
//     *
//     * @param id          服务商id
//     * @param interfaceId 支付接口id
//     * @return 服务商支付接口配置详情
//     */
//    @GetMapping("/{id}/{interfaceId}")
//    public PayInterfaceConfigVO getIsvConfigInfo(@PathVariable(value = "id") Long id, @PathVariable(value = "interfaceId") Long interfaceId) {
//        return payInterfaceConfigService.getConfigInfo(PayClientType.SERVICE_PROVIDER, id, interfaceId);
//    }
//
//
//    /**
//     * 保存或更新支付配置
//     *
//     * @param payInterfaceConfigDTO 支付配置请求体
//     */
//    @PostMapping
//    public Boolean saveOrUpdate(@RequestBody @Validated ClientPayInterfaceConfigDTO payInterfaceConfigDTO) {
//        return payInterfaceConfigService.payConfigurationSaveOrUpdate(payInterfaceConfigDTO, PayClientType.SERVICE_PROVIDER, payInterfaceConfigDTO.getClientId(), Boolean.FALSE);
//    }
//
//    /**
//     * 获取服务商支付配置列表
//     */
//    @GetMapping("/list/{isvId}")
//    public List<PayInterfaceConfigListVO> getInterfaceConfigList(@PathVariable(value = "isvId") Long isvId) {
//        return payInterfaceConfigAppService.getIsvInterfaceConfigList(isvId, PayClientType.SERVICE_PROVIDER);
//    }
//
//    /**
//     * 获取当前商户是否需要进行后续执行
//     *
//     * @param interfaceId 接口id
//     * @param mchId       商户id
//     */
//    @GetMapping("/option/{mchId}/{interfaceId}")
//    public List<String> trailingOption(@PathVariable("interfaceId") Long interfaceId, @PathVariable("mchId") Long mchId) {
//        return payInterfaceConfigService.trailingOption(interfaceId, mchId);
//    }


    /**
     * 查询已签约的支付方式的列表
     */
    @GetMapping("/list/{isvId}")
    public List<PayInterfaceConfigVO> getPayInterfaceConfigs(@PathVariable("isvId")Long isvId){
        return payInterfaceConfigService.getIsvPayInterfaceConfigs(isvId);
    }

    /**
     * 更新已签约的支付方式的列表
     */
    @PutMapping
    public Boolean getPayInterfaceConfigs(@RequestBody PayInterfaceConfigVO payInterfaceConfigVO){
        return payInterfaceConfigService.updatePayInterfaceConfig(payInterfaceConfigVO);
    }

}
