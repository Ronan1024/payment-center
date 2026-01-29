package com.baosight.payment.system.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayInterfaceDefineService;
import com.baosight.security.annotation.AllowAccess;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 支付接口管理
 *
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping( "/pm/pay/interface/define")
@AllowAccess
public class PayInterfaceDefineController {

    private final PayInterfaceDefineService payInterfaceDefineService;
    @Resource
    private PayInterfaceConfigService payInterfaceConfigService;

    /**
     * 获取支付接口列表
     */
    @PostMapping("/page")
    public List<PayInterfaceDefineListVO> list() {
        return payInterfaceDefineService.selectList();
    }

    /**
     * 获取支付接口列表
     */
    @PostMapping("/list")
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
    public Boolean insert(@RequestBody @Validated PayInterFaceDefineDTO payInterFaceDefine) {
        return payInterfaceDefineService.insert(payInterFaceDefine);
    }

    /**
     * 更新支付接口
     */
    @PutMapping
    public Boolean update(@RequestBody @Validated PayInterFaceDefineDTO payInterFaceDefineDTO) {
        return payInterfaceDefineService.updatePayInterface(payInterFaceDefineDTO);
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
        consumer.accept(result, isvId);
        return result;
    }

    @GetMapping("/service/provider/list/mch/{mchId}")
    public List<PayInterfaceDefineListVO> mchPayInterfaceDefineList(@PathVariable("mchId") Long mchId) {
        List<PayInterfaceDefineListVO> result = payInterfaceDefineService.mchPayInterfaceDefineList(mchId);
        consumer.accept(result, mchId);
        return result;
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
