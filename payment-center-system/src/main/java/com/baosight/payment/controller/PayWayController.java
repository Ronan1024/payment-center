package com.baosight.payment.controller;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.pojo.dto.PayWayPageDTO;
import com.baosight.payment.pojo.dto.SavePayWayDTO;
import com.baosight.payment.pojo.vo.PayWayPageVO;
import com.baosight.payment.pojo.vo.PayWayVO;
import com.baosight.payment.service.PayWayService;
import com.baosight.saas.constant.BaseUrlConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付方式控制台
 */
@RestController
@RequiredArgsConstructor
@EnableFeignClients
@RequestMapping(BaseUrlConstant.SYSTEM + "/pm/pay/way/manage")
public class PayWayController {

    private final PayWayService payWayService;

    /**
     * 支付方式列表
     */
    @PostMapping("/page")
    public PageResponse<PayWayPageVO> page(@RequestBody @Validated PayWayPageDTO payWayDTO) {
        return payWayService.pagePayWay(payWayDTO);
    }


    /**
     * 支付方式列表
     */
    @GetMapping("/list")
    public List<PayWayPageVO> list() {
        return payWayService.pagePayList();
    }

    @GetMapping("/{id}")
    public PayWayVO detail(@PathVariable("id") Long id) {
        return payWayService.detailPayWay(id);
    }


    /**
     * 新增支付方式
     */
    @PostMapping
    public Boolean savePayWay(@RequestBody @Validated SavePayWayDTO payWayDTO) {
        return payWayService.savePayWay(payWayDTO);
    }


    @PutMapping("/{id}")
    public Boolean update(@PathVariable("id") Long id, @RequestBody @Validated SavePayWayDTO payWayDTO) {
        return payWayService.updatePayWay(id, payWayDTO);
    }

    /**
     * 删除支付方式
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return payWayService.deletePayWay(id);
    }


    /**
     * 禁用支付方式
     */
    @PutMapping("/disable/{id}")
    public Boolean disable(@PathVariable("id") Long id) {
        return payWayService.disablePayWay(id);
    }
}
