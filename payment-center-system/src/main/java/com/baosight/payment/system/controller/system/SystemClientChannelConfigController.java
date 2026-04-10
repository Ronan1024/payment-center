package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.dto.req.ClientChannelConfigReqDTO;
import com.baosight.payment.system.pojo.dto.req.MchChannelPermissionReqDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelConfigRespDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientChannelRespDTO;
import com.baosight.payment.system.pojo.validation.InsertChannelConfigGroup;
import com.baosight.payment.system.service.SystemMchChannelConfigFlowService;
import com.baosight.payment.system.service.SystemMchChannelConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 运营端支付通道配置管理
 *
 * @author L.J.Rab
 */
@RestController
@RequiredArgsConstructor
//@RequestMapping(SYSTEM + "/pm/pay/isv/config/manage")
@RequestMapping("/pay/config/manage")
public class SystemClientChannelConfigController {
    private final SystemMchChannelConfigService systemMchChannelConfigService;
    private final SystemMchChannelConfigFlowService systemMchChannelConfigFlowService;


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
     * 保存商户渠道权限
     */
    @PostMapping
    public Boolean saveMchChannel(@RequestBody @Validated MchChannelPermissionReqDTO mchChannelPermission) {
        return systemMchChannelConfigService.saveClientChannelPermission(mchChannelPermission);
    }


    /**
     * 获取商户已有的支付渠道权限
     *
     * @param type  当前商户类型
     * @param mchId 当前商户id
     */
    @GetMapping
    public List<String> getMchChannel(@RequestParam("type") Integer type, @RequestParam("mchId") Long mchId) {
        return systemMchChannelConfigService.getMchChannel(type, mchId);
    }


    /**
     * 查询已签约的支付方式的列表
     */
    @GetMapping("/{clientId}")
    public List<ClientChannelRespDTO> mchChannelList(@PathVariable("clientId") Long clientId) {
        return systemMchChannelConfigService.mchChannelList(clientId);
    }


    /**
     * 保存商户支付通道配置信息
     */
    @PostMapping("/channel")
    public Boolean saveClientChannelConfig(@RequestBody @Validated(InsertChannelConfigGroup.class) ClientChannelConfigReqDTO channelConfigReq) {
        String channelCode = systemMchChannelConfigService.saveClientChannelConfig(channelConfigReq);
        if (StringUtils.hasText(channelCode)) {
            // 进行渠道后续流程执行
            systemMchChannelConfigFlowService.execute(channelConfigReq.getClientId(), channelConfigReq.getClientType(), channelCode, "");
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 获取商户支付通道配置信息
     */
    @GetMapping("/channel/info")
    public ClientChannelConfigRespDTO clientChannelConfigInfo(@RequestParam("channelId") Long channelId, @RequestParam("clientId") Long clientId, @RequestParam("type") Integer type) {
        return systemMchChannelConfigService.clientChannelConfigInfo(channelId, clientId, type);
    }


    /**
     * 执行通道流程
     */
    @Deprecated
    @PostMapping("/channel/execute")
    public Boolean executeChannelProcess(@RequestParam("clientId") Long clientId, @RequestParam("channel_code") String channelCode, @RequestBody Object body) {
        return systemMchChannelConfigService.executeChannelProcess(clientId, channelCode, body);
    }


//
//    /**
//     * 更新已签约的支付方式的列表
//     */
//    @PutMapping
//    public Boolean getPayInterfaceConfigs(@RequestBody PayInterfaceConfigDynamicVO payInterfaceConfigDynamicVO) {
//        return payInterfaceConfigService.updatePayInterfaceConfig4Isv(payInterfaceConfigDynamicVO);
//    }

//    /**
//     * 为商户绑定支付接口
//     */
//    @PostMapping("/bind/{isvId}/{payInterfaceId}")
//    public Boolean addPayInterfaceConfig(@PathVariable("isvId") Long isvId, @PathVariable("payInterfaceId") Long payInterfaceId){
//        return payInterfaceConfigService.addPayInterfaceConfig(isvId, payInterfaceId);
//    }

}
