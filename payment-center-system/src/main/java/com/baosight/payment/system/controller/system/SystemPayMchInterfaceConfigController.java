package com.baosight.payment.system.controller.system;

import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.system.pojo.dto.PayInterfaceDefineBindDTO;
import com.baosight.payment.system.pojo.vo.PayInterfaceConfigDynamicVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商户支付配置管理
 *
 * @program: payment-center
 * @description: 平台管理商户支付配置
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


//    /**
//     * 获取服务支付接口配置信息
//     *
//     * @param id          商户id
//     * @param interfaceId 支付接口id
//     * @return 服务商支付接口配置详情
//     */
//    @GetMapping("/{id}/{interfaceId}")
//    public PayInterfaceConfigVO getIsvConfigInfo(@PathVariable(value = "id") Long id, @PathVariable(value = "interfaceId") Long interfaceId) {
//        PayClientType payClientType = function.apply(id);
//        return payInterfaceConfigService.getConfigInfo(payClientType, id, interfaceId);
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
//        PayClientType payClientType = function.apply(payInterfaceConfigDTO.getClientId());
//        return payInterfaceConfigService.payConfigurationSaveOrUpdate(payInterfaceConfigDTO, payClientType, payInterfaceConfigDTO.getClientId(), Boolean.TRUE);
//    }
//
//    /**
//     * 获取商户支付配置列表
//     */
//    @GetMapping("/list/{mchId}")
//    public List<PayInterfaceConfigListVO> getInterfaceConfigList(@PathVariable(value = "mchId") Long mchId) {
//        return payInterfaceConfigService.getMchInterfaceConfigList(mchId);
//    }
//
//    /**
//     * 根据商户id 获取商户类型
//     */
//    private final LongFunction<PayClientType> function = mchId -> {
//        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
//        Assert.isNull(mchInfoVO, ApiException.supplier(MchError.MCH_NOT_FOUND));
//        return IBaseEnum.getByCode(PayClientType.class, mchInfoVO.getType());
//    };

    /**
     * 查询商户已绑定的支付配置列表
     */
    @GetMapping("/list/{mchId}")
    public List<PayInterfaceConfigDynamicVO> getPayInterfaceConfigs(@PathVariable("mchId")Long mchId){
        return payInterfaceConfigService.getMchPayInterfaceConfigs(mchId);
    }

    /**
     * 更新已绑定的支付方式的列表
     */
    @PutMapping
    public Boolean updatePayInterfaceConfigs(@RequestBody PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO){
        return payInterfaceConfigService.updatePayInterfaceConfig4Mch(payInterfaceConfigDynamicVO);
    }

    /**
     * 为商户绑定支付接口
     */
    @PostMapping("/bind")
    public Boolean addPayInterfaceConfig(@RequestBody PayInterfaceDefineBindDTO payInterfaceDefineBindDTO){
        return payInterfaceConfigService.addPayInterfaceConfig(payInterfaceDefineBindDTO);
    }

    /**
     * 为特约商户绑定服务商
     */
    @PostMapping("/bind/isv//{mchId}/{isvId}")
    public Boolean addPayInterfaceConfig(@PathVariable("mchId") Long mchId,@PathVariable("isvId") Long isvId){
        return payInterfaceConfigService.bindIsv(mchId,isvId);
    }
}
