package com.baosight.payment.controller;

import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.pojo.dto.PayInterfacePageDTO;
import com.baosight.payment.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.pojo.vo.PayInterfaceDefineVO;
import com.baosight.payment.service.PayInterfaceDefineService;
import com.baosight.payment.service.PayWayService;
import com.baosight.saas.constant.BaseUrlConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付接口定义
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseUrlConstant.SYSTEM + "/pm/pay/interface/define")
public class PayInterfaceDefineController {

    private final PayWayService payWayService;
    private final PayInterfaceDefineService payInterfaceDefineService;

    /**
     * 获取支付接口列表
     */
    @PostMapping("/page")
    public List<PayInterfaceDefineListVO> page(@RequestBody @Validated PayInterfacePageDTO pageDTO) {
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
        return payInterfaceDefineService.getPayInterfaceDefineList(PayClientType.SERVICE_PROVIDER, isvId);

    }
}
