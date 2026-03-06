package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.dto.resp.PayingAgencyRespDTO;
import com.baosight.payment.system.service.PayingAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 支付机构管理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/paying/agency/manager")
public class SystemPayingAgencyController {

    private final PayingAgencyService payingAgencyService;

    /**
     * 支付机构列表
     */
    @GetMapping("/list")
    public List<PayingAgencyRespDTO> payingAgencyList() {
        return payingAgencyService.payingAgencyList();
    }


}
