package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.dto.req.SavePaymentChannelFlowReqDTO;
import com.baosight.payment.system.pojo.dto.resp.PaymentChannelFlowRespDTO;
import com.baosight.payment.system.service.SystemChannelFlowDefineService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 运营端管理支付渠道流程管理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/26
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/channel/flow")
public class SystemPaymentChannelFlowController {

    private final SystemChannelFlowDefineService systemChannelFlowDefineService;

    /**
     * 获取支付渠道流程列表
     */
    @GetMapping("/list/{channelCode}")
    public List<PaymentChannelFlowRespDTO> list(@PathVariable("channelCode") String channelCode) {
        return systemChannelFlowDefineService.list(channelCode);
    }

    /**
     * 新增支付渠道流程
     */
    @PostMapping
    public Boolean add(@RequestBody @Validated SavePaymentChannelFlowReqDTO saveDTO) {
        return systemChannelFlowDefineService.add(saveDTO);
    }

    /**
     * 删除支付渠道流程
     */
    @DeleteMapping("/{channelCode}")
    public Boolean delete(@PathVariable String channelCode, @RequestParam Long id) {
        return systemChannelFlowDefineService.delete(channelCode, id);
    }

    /**
     * 获取渠道流程类型列表
     */
    @GetMapping("/type/list/{channelCode}")
    public Map<String, String> listChannelFlowType(@PathVariable String channelCode) {
        return systemChannelFlowDefineService.listChannelFlowType(channelCode);
    }

}
