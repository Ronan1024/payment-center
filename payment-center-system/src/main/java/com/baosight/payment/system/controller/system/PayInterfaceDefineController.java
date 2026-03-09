package com.baosight.payment.system.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.dto.req.PayInterFaceDefineReqDTO;
import com.baosight.payment.system.pojo.dto.resp.PayingChannelDefineListRespDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.validation.InsertChannelDefineGroup;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 支付接口管理(支付渠道配置管理)
 *
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pm/pay/interface/define")
public class PayInterfaceDefineController {

    private final PayInterfaceDefineService payInterfaceDefineService;


    @Resource
    private PayInterfaceConfigService payInterfaceConfigService;

    /**
     * 获取支付接口列表(商户绑定支付接口使用)
     *
     * @param payClientType 取值参见PayClientType
     */
    @PostMapping("/list/{payClientType}")
    public List<PayInterfaceDefineListVO> list(@PathVariable("payClientType") Integer payClientType) {
        return payInterfaceDefineService.queryList(payClientType);
    }

    /**
     * 获取支付接口列表
     */
    @PostMapping("/page")
    public PageResponse<PayInterfaceDefineListVO> page(@RequestBody @Validated PayInterfaceListDTO pageDTO) {
        return payInterfaceDefineService.payInterfacePage(pageDTO);
    }


    /**
     * 获取支付接口定义详情
     */
    @GetMapping("/{id}")
    public PayInterfaceDefineVO detail(@PathVariable("id") Long id) {
        return payInterfaceDefineService.detail(id);
    }


    /**
     * 新增支付接口
     */
    @PostMapping
    public Boolean insert(@RequestBody @Validated(InsertChannelDefineGroup.class) PayInterFaceDefineReqDTO payInterFaceDefine) {
        return payInterfaceDefineService.insert(payInterFaceDefine);
    }

    /**
     * 更新支付接口
     */
    @PutMapping("/{id}")
    public Boolean update(@RequestBody @Validated(InsertChannelDefineGroup.class) PayInterFaceDefineReqDTO payInterFaceDefineDTO, @PathVariable("id") Long id) {
        return payInterfaceDefineService.updatePayInterface(payInterFaceDefineDTO, id);
    }

    /**
     * 删除支付接口
     *
     * @param id 需要删除的支付接口Id
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return payInterfaceDefineService.delete(id);
    }


    /**
     * 修改支付通道状态
     */
    @PutMapping("/enable/{id}")
    public void editEnable(@PathVariable("id") Long id) {
        payInterfaceDefineService.editEnable(id);
    }


    /**
     * 服务商获取支付接口配置列表放
     */
    @Deprecated
    @GetMapping("/service/provider/list/{isvId}")
    public List<PayInterfaceDefineListVO> payInterfaceDefineList(@PathVariable("isvId") Long isvId) {
        List<PayInterfaceDefineListVO> result = payInterfaceDefineService.getPayInterfaceDefineList(PayClientType.SERVICE_PROVIDER);
        consumer.accept(result, isvId);
        return result;
    }

    @Deprecated
    @GetMapping("/service/provider/list/mch/{mchId}")
    public List<PayInterfaceDefineListVO> mchPayInterfaceDefineList(@PathVariable("mchId") Long mchId) {
        List<PayInterfaceDefineListVO> result = payInterfaceDefineService.mchPayInterfaceDefineList(mchId);
        consumer.accept(result, mchId);
        return result;
    }

    /**
     * 获取支付通道列表信息
     *
     * @param mchType 商户类型
     */
    @GetMapping("/list/{type}")
    public List<PayingChannelDefineListRespDTO> channelDefineList(@PathVariable("type") Integer mchType) {
        return payInterfaceDefineService.channelDefineList(mchType);
    }


    /**
     * 处理支付支付参数信息
     */
    private BiConsumer<List<PayInterfaceDefineListVO>, Long> consumer = (result, clientId) -> {
        Map<Long, PayInterfaceConfig> payConfigurationMap = payInterfaceConfigService.getPayConfigurationMap(clientId);
        result.forEach(e -> {
            if (payConfigurationMap.containsKey(e.getId())) {
                e.setEnable(payConfigurationMap.get(e.getId()).getEnable());
            } else {
                e.setEnable(Boolean.FALSE);
            }
        });
    };
}
