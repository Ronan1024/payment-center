package com.baosight.payment.channel.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfacePageReqDTO;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfaceStatusUpdateReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfaceInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfacePageRespDTO;
import com.baosight.payment.channel.service.ChannelInterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 运营端渠道接口管理控制器
 *
 * <p>
 * 面向运营后台提供渠道接口能力的查询和状态维护能力。渠道接口用于描述某个渠道处理器
 * 在指定能力域、动作、支付品牌和支付场景下可提供的具体接口能力。
 * </p>
 *
 * @program: payment-center
 * @description: 运营端渠道接口管理
 * @author: L.J.Ran
 * @create: 2026/6/12
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel/interface/manager")
public class SystemChannelInterfaceController {

    private final ChannelInterfaceService channelInterfaceService;

    /**
     * 分页查询渠道接口
     */
    @PostMapping("/page")
    public PageResponse<ChannelInterfacePageRespDTO> page(@RequestBody ChannelInterfacePageReqDTO req) {
        return channelInterfaceService.page(req);
    }

    /**
     * 查看渠道接口详情
     */
    @GetMapping("/{id}")
    public ChannelInterfaceInfoRespDTO info(@PathVariable Long id) {
        return channelInterfaceService.info(id);
    }

    /**
     * 修改渠道接口状态
     */
    @PutMapping("/status")
    public Boolean updateStatus(@RequestBody @Validated ChannelInterfaceStatusUpdateReqDTO req) {
        return channelInterfaceService.updateStatus(req);
    }
}
