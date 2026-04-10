package com.baosight.payment.system.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.dto.req.PayInterFaceDefineReqDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientPayChannelDefineRespDTO;
import com.baosight.payment.system.pojo.dto.resp.PayingChannelDefineListRespDTO;
import com.baosight.payment.system.pojo.validation.InsertChannelDefineGroup;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.SystemClientChannelDefineService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运营端支付渠道配置定义管理
 *
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pm/pay/interface/define")
public class SystemClientChannelDefineController {

    private final SystemClientChannelDefineService payInterfaceDefineService;

    private final PayInterfaceConfigService payInterfaceConfigService;

    /**
     * 获取支付接口列表(商户绑定支付接口使用)
     *
     * @param payClientType 取值参见PayClientType
     */
    @Deprecated
    @PostMapping("/list/{payClientType}")
    public List<PayInterfaceDefineListVO> list(@PathVariable("payClientType") Integer payClientType) {
        return payInterfaceDefineService.queryList(payClientType);
    }

    /**
     * 获取支付渠道配置定义分页列表
     */
    @PostMapping("/page")
    public PageResponse<PayInterfaceDefineListVO> page(@RequestBody @Validated PayInterfaceListDTO pageDTO) {
        return payInterfaceDefineService.payInterfacePage(pageDTO);
    }


    /**
     * 获取支付渠道配置定义详情
     */
    @GetMapping("/{id}")
    public PayInterfaceDefineVO detail(@PathVariable("id") Long id) {
        return payInterfaceDefineService.detail(id);
    }


    /**
     * 添加支付渠道配置定义信息
     */
    @PostMapping
    public Boolean insert(@RequestBody @Validated(InsertChannelDefineGroup.class) PayInterFaceDefineReqDTO payInterFaceDefine) {
        return payInterfaceDefineService.insert(payInterFaceDefine);
    }

    /**
     * 更新支付渠道配置定义信息
     */
    @PutMapping("/{id}")
    public Boolean update(@RequestBody @Validated(InsertChannelDefineGroup.class) PayInterFaceDefineReqDTO payInterFaceDefineDTO, @PathVariable("id") Long id) {
        return payInterfaceDefineService.updatePayInterface(payInterFaceDefineDTO, id);
    }

    /**
     * 删除支付渠道配置定义信息
     *
     * @param id 需要删除的支付接口Id
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return payInterfaceDefineService.delete(id);
    }


    /**
     * 修改支付渠道配置定义状态
     */
    @PutMapping("/enable/{id}")
    public void editEnable(@PathVariable("id") Long id) {
        payInterfaceDefineService.editEnable(id);
    }


    /**
     * 获取客户端支付渠道配置定义信息
     *
     * @param clientId  客户端id
     * @param type      客户端类型
     * @param channelId 渠道id
     */
    @GetMapping("/client/channel/define")
    public ClientPayChannelDefineRespDTO clientChannelDefine(@RequestParam("clientId") Long clientId, @RequestParam("type") Integer type, @RequestParam("channelId") Long channelId) {
        return payInterfaceDefineService.clientChannelDefine(clientId, type, channelId);
    }


    /**
     * 服务商获取支付接口配置列表放
     */
    @Deprecated
    @GetMapping("/service/provider/list/{isvId}")
    public List<PayInterfaceDefineListVO> payInterfaceDefineList(@PathVariable("isvId") Long isvId) {
        List<PayInterfaceDefineListVO> result = payInterfaceDefineService.getPayInterfaceDefineList(PayClientType.SERVICE_PROVIDER);
//        consumer.accept(result, isvId);
        return result;
    }

    @Deprecated
    @GetMapping("/service/provider/list/mch/{mchId}")
    public List<PayInterfaceDefineListVO> mchPayInterfaceDefineList(@PathVariable("mchId") Long mchId) {
        List<PayInterfaceDefineListVO> result = payInterfaceDefineService.mchPayInterfaceDefineList(mchId);
//        consumer.accept(result, mchId);
        return result;
    }

    /**
     * 获取客户端支付渠道配置定义信息
     *
     * @param clientId   客户端id
     * @param clientType 客户端类型
     */
    @GetMapping("/list")
    public List<PayingChannelDefineListRespDTO> channelDefineList(@RequestParam("clientType") Integer clientType, @RequestParam("clientId") Long clientId) {
        return payInterfaceDefineService.channelDefineList(clientType, clientId);
    }

//
//    /**
//     * 处理支付支付参数信息
//     */
//    private BiConsumer<List<PayInterfaceDefineListVO>, Long> consumer = (result, clientId) -> {
//        Map<Long, PayInterfaceConfig> payConfigurationMap = payInterfaceConfigService.getPayConfigurationMap(clientId);
//        result.forEach(e -> {
//            if (payConfigurationMap.containsKey(e.getId())) {
//                e.setEnable(payConfigurationMap.get(e.getId()).getEnable());
//            } else {
//                e.setEnable(Boolean.FALSE);
//            }
//        });
//    };
}
