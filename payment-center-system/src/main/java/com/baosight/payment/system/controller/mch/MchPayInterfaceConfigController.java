package com.baosight.payment.system.controller.mch;

import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.payment.system.service.app.PayInterfaceConfigAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 服务商支付接口配置
 *
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping( "/isv/pay/interface/define")
public class MchPayInterfaceConfigController {

    private final PayInterfaceDefineService payInterfaceDefineService;
    private final PayInterfaceConfigService payInterfaceConfigService;
    private final PayInterfaceConfigAppService payInterfaceConfigAppService;

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
     * 获取支付接口配置列表
     */
    @GetMapping("/service/provider/list")
    public List<PayInterfaceDefineListVO> payInterfaceDefineList() {
        List<PayInterfaceDefineListVO> result = payInterfaceDefineService.getPayInterfaceDefineList(PayClientType.SERVICE_PROVIDER);
//        Long mchId = SystemUserContext.getCompanyId();
        // TODO 商户id
        Long mchId = 0L;
        // 获取支付参数信息
        Map<Long, PayInterfaceConfig> payConfigurationMap = payInterfaceConfigService.getPayConfigurationMap(mchId);
        result.stream().filter(e -> payConfigurationMap.containsKey(e.getId())).forEach(e -> e.setEnable(Boolean.TRUE));
        return result;

    }
}
