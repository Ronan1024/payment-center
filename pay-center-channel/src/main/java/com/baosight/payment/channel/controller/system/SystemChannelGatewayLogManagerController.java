package com.baosight.payment.channel.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.pojo.dto.req.ChannelGatewayLogPageReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelGatewayLogInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelGatewayLogPageRespDTO;
import com.baosight.payment.channel.service.ChannelGatewayLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 运营端渠道网关日志管理
 * @program: payment-center
 * @description: 运营端渠道网关日志管理
 * @author: L.J.Ran
 * @create: 2026/4/17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel/gateway/log/manager")
public class SystemChannelGatewayLogManagerController {

    private final ChannelGatewayLogService channelGatewayLogService;

    /**
     * 渠道网关日志记录分页
     */
    @PostMapping("/page")
    public PageResponse<ChannelGatewayLogPageRespDTO> page(@RequestBody @Validated ChannelGatewayLogPageReqDTO channelGatewayLogPage) {
        return channelGatewayLogService.page(channelGatewayLogPage);
    }

    /**
     * 渠道网关日志记录详情
     */
    @GetMapping("/{id}")
    public ChannelGatewayLogInfoRespDTO info(@PathVariable Long id) {
        return channelGatewayLogService.info(id);
    }

}
