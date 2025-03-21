package com.baosight.payment.system.controller.isv;

import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.payment.system.service.PayWayService;
import com.baosight.saas.constant.BaseUrlConstant;
import com.baosight.utils.stream.StreamBuild;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 支付接口定义
 *
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseUrlConstant.SYSTEM + "/isv/pay/config")
public class IsvPayInterfaceConfigController {

    private final PayWayService payWayService;
    private final PayInterfaceDefineService payInterfaceDefineService;
    private final PayInterfaceConfigService payInterfaceConfigService;

    /**
     * 获取支付接口列表
     */
    @PostMapping("/page")
    public List<PayInterfaceDefineListVO> page(@RequestBody @Validated PayInterfaceListDTO pageDTO) {
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
    public Boolean insert(@RequestBody @Validated PayInterFaceDefineDTO payInterFaceDefine) {
        payWayService.verify(payInterFaceDefine.getPayWayList());
        return payInterfaceDefineService.insert(payInterFaceDefine);
    }

    /**
     * 更新支付接口
     */
    @PutMapping("/{id}")
    public Boolean update(@PathVariable("id") Long id, @RequestBody @Validated PayInterFaceDefineDTO payInterFaceDefineDTO) {
        payWayService.verify(payInterFaceDefineDTO.getPayWayList());
        return payInterfaceDefineService.updatePayInterface(id, payInterFaceDefineDTO);
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
     * 服务商获取支付接口配置列表
     */
    @GetMapping("/service/provider/list/{isvId}")
    public List<PayInterfaceDefineListVO> payInterfaceDefineList(@PathVariable("isvId") Long isvId) {
        List<PayInterfaceDefineListVO> result = payInterfaceDefineService.getPayInterfaceDefineList(PayClientType.SERVICE_PROVIDER);
        // 获取支付参数信息
        List<PayInterfaceConfig> payInterfaceConfigList = payInterfaceConfigService.getPayConfiguration(PayClientType.SERVICE_PROVIDER, isvId);
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
